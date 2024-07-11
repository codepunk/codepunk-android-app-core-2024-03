package com.codepunk.skeleton.data.mapperv2

import androidx.compose.ui.util.fastForEachReversed
import com.codepunk.skeleton.data.local.type.ArtistReferenceType
import com.codepunk.skeleton.data.local.type.LabelReferenceType
import com.codepunk.skeleton.data.local.type.ResourceDetailType
import com.codepunk.skeleton.data.localv2.entity.LocalArtist
import com.codepunk.skeleton.data.localv2.entity.LocalArtistReference
import com.codepunk.skeleton.data.localv2.entity.LocalCreditReference
import com.codepunk.skeleton.data.localv2.entity.LocalImage
import com.codepunk.skeleton.data.localv2.entity.LocalLabel
import com.codepunk.skeleton.data.localv2.entity.LocalLabelReference
import com.codepunk.skeleton.data.localv2.entity.LocalMaster
import com.codepunk.skeleton.data.localv2.entity.LocalResource
import com.codepunk.skeleton.data.localv2.entity.LocalResourceDetail
import com.codepunk.skeleton.data.localv2.entity.LocalTrack
import com.codepunk.skeleton.data.localv2.entity.LocalVideo
import com.codepunk.skeleton.data.localv2.relation.LocalArtistWithDetails
import com.codepunk.skeleton.data.localv2.relation.LocalLabelWithDetails
import com.codepunk.skeleton.data.localv2.relation.LocalMasterWithDetails
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndArtist
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndLabel
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndMaster
import com.codepunk.skeleton.data.localv2.relation.LocalTrackWithDetails
import com.codepunk.skeleton.data.remotev2.entity.RemoteArtist
import com.codepunk.skeleton.data.remotev2.entity.RemoteArtistReference
import com.codepunk.skeleton.data.remotev2.entity.RemoteCreditReference
import com.codepunk.skeleton.data.remotev2.entity.RemoteImage
import com.codepunk.skeleton.data.remotev2.entity.RemoteLabel
import com.codepunk.skeleton.data.remotev2.entity.RemoteLabelReference
import com.codepunk.skeleton.data.remotev2.entity.RemoteMaster
import com.codepunk.skeleton.data.remotev2.entity.RemoteResource
import com.codepunk.skeleton.data.remotev2.entity.RemoteTrack
import com.codepunk.skeleton.data.remotev2.entity.RemoteVideo
import com.codepunk.skeleton.domainv2.model.Artist
import com.codepunk.skeleton.domainv2.model.ArtistReference
import com.codepunk.skeleton.domainv2.model.CreditReference
import com.codepunk.skeleton.domainv2.model.Image
import com.codepunk.skeleton.domainv2.model.Label
import com.codepunk.skeleton.domainv2.model.LabelReference
import com.codepunk.skeleton.domainv2.model.Master
import com.codepunk.skeleton.domainv2.model.Track
import com.codepunk.skeleton.domainv2.model.Video
import com.codepunk.skeleton.util.parseElapsedTimeString
import com.codepunk.skeleton.util.toElapsedTimeString
import java.util.Stack
import kotlin.time.DurationUnit

// region Methods

// ====================
// Resource
// ====================

fun RemoteResource.toLocalResource() = LocalResource(
    resourceUrl = resourceUrl,
    uri = uri,
    dataQuality = dataQuality
)

// ====================
// Resource detail
// ====================

fun List<LocalResourceDetail>.toDomainDetails(
    detailType: ResourceDetailType
): List<String> = filter { it.detailType == detailType }
    .sortedBy { it.detailIdx }
    .map { it.detail }

fun List<String>.toLocalResourceDetails(
    detailType: ResourceDetailType
) = mapIndexed { index, detail ->
    LocalResourceDetail(
        detailType = detailType,
        detailIdx = index,
        detail = detail
    )
}

// ====================
// Image
// ====================

fun LocalImage.toDomainImage() = Image(
    type = type,
    uri = uri,
    resourceUrl = resourceUrl,
    uri150 = uri150,
    width = width,
    height = height
)

fun RemoteImage.toLocalImage(index: Int): LocalImage = LocalImage(
    imageIdx = index,
    type = type,
    uri = uri,
    resourceUrl = resourceUrl,
    uri150 = uri150,
    width = width,
    height = height
)

// ====================
// Artist
// ====================

fun LocalResourceAndArtist.toDomainArtist(): Artist = Artist(
    id = artistWithDetails.artist.artistId,
    resourceUrl = resource.resourceUrl,
    uri = resource.uri,
    images = artistWithDetails.images.map { it.toDomainImage() },
    dataQuality = resource.dataQuality,
    name = artistWithDetails.artist.name,
    profile = artistWithDetails.artist.profile,
    releasesUrl = artistWithDetails.artist.releasesUrl,
    urls = artistWithDetails.details.toDomainDetails(ResourceDetailType.URL),
    realName = artistWithDetails.artist.realName,
    nameVariations = artistWithDetails.details.toDomainDetails(ResourceDetailType.NAME_VARIATION),
    aliases = artistWithDetails.artistRefs.toDomainArtistReferences(ArtistReferenceType.ALIAS),
    members = artistWithDetails.artistRefs.toDomainArtistReferences(ArtistReferenceType.MEMBER),
    groups = artistWithDetails.artistRefs.toDomainArtistReferences(ArtistReferenceType.GROUP)
)

fun RemoteArtist.toLocalResourceAndArtist(): LocalResourceAndArtist = LocalResourceAndArtist(
    resource = toLocalResource(),
    artistWithDetails = toLocalArtistWithDetails()
)

fun RemoteArtist.toLocalArtist(): LocalArtist = LocalArtist(
    artistId = id,
    realName = realName,
    name = name,
    profile = profile,
    releasesUrl = releasesUrl
)

fun RemoteArtist.toLocalArtistWithDetails(): LocalArtistWithDetails =
    LocalArtistWithDetails(
        artist = toLocalArtist(),
        images = images.mapIndexed { index, image -> image.toLocalImage(index) },
        details = urls.toLocalResourceDetails(ResourceDetailType.URL) +
                nameVariations.toLocalResourceDetails(ResourceDetailType.NAME_VARIATION),
        artistRefs = aliases.toLocalArtistReferences(ArtistReferenceType.ALIAS) +
                members.toLocalArtistReferences(ArtistReferenceType.MEMBER) +
                groups.toLocalArtistReferences(ArtistReferenceType.GROUP)
    )

// ====================
// Artist reference
// ====================

fun LocalArtistReference.toDomainArtistReference() = ArtistReference(
    id = artistId,
    name = name,
    resourceUrl = resourceUrl,
    active = active,
    thumbnailUrl = thumbnailUrl
)

fun List<LocalArtistReference>.toDomainArtistReferences(
    referenceType: ArtistReferenceType
): List<ArtistReference> = filter { it.referenceType == referenceType }
    .map { it.toDomainArtistReference() }

fun List<RemoteArtistReference>.toLocalArtistReferences(
    referenceType: ArtistReferenceType
) = map { ref ->
    LocalArtistReference(
        referenceType = referenceType,
        artistId = ref.id,
        name = ref.name,
        resourceUrl = ref.resourceUrl,
        active = ref.active,
        thumbnailUrl = ref.thumbnailUrl
    )
}

// ====================
// Label
// ====================

fun LocalResourceAndLabel.toDomainLabel(): Label = Label(
    id = labelWithDetails.label.labelId,
    resourceUrl = resource.resourceUrl,
    uri = resource.uri,
    images = labelWithDetails.images.map { it.toDomainImage() },
    dataQuality = resource.dataQuality,
    name = labelWithDetails.label.name,
    profile = labelWithDetails.label.profile,
    releasesUrl = labelWithDetails.label.releasesUrl,
    urls = labelWithDetails.details.toDomainDetails(ResourceDetailType.URL),
    contactInfo = labelWithDetails.label.contactInfo,
    parentLabel = labelWithDetails.labelRefs.toDomainLabelReferences(
        LabelReferenceType.PARENT_LABEL
    ).firstOrNull(),
    subLabels = labelWithDetails.labelRefs.toDomainLabelReferences(LabelReferenceType.SUB_LABEL)
)

fun RemoteLabel.toLocalResourceAndLabel(): LocalResourceAndLabel = LocalResourceAndLabel(
    resource = toLocalResource(),
    labelWithDetails = toLocalLabelWithDetails()
)

fun RemoteLabel.toLocalLabel(): LocalLabel = LocalLabel(
    labelId = id,
    name = name,
    profile = profile,
    releasesUrl = releasesUrl,
    contactInfo = contactInfo
)

fun RemoteLabel.toLocalLabelWithDetails(): LocalLabelWithDetails =
    LocalLabelWithDetails(
        label = toLocalLabel(),
        images = images.mapIndexed { index, image -> image.toLocalImage(index) },
        details = urls.toLocalResourceDetails(ResourceDetailType.URL),
        labelRefs = parentLabel.toLocalLabelReferences() +
                subLabels.toLocalLabelReferences(LabelReferenceType.SUB_LABEL)
    )

// ====================
// Label reference
// ====================

fun LocalLabelReference.toDomainLabelReference() = LabelReference(
    id = labelId,
    name = name,
    resourceUrl = resourceUrl
)

fun List<LocalLabelReference>.toDomainLabelReferences(
    referenceType: LabelReferenceType
): List<LabelReference> = filter { it.referenceType == referenceType }
    .map { it.toDomainLabelReference() }

fun List<RemoteLabelReference>.toLocalLabelReferences(
    referenceType: LabelReferenceType
) = map { ref ->
    LocalLabelReference(
        referenceType = referenceType,
        labelId = ref.id,
        name = ref.name,
        resourceUrl = ref.resourceUrl
    )
}

// TODO Is there a way to avoid parentLabel being a list?
fun RemoteLabelReference?.toLocalLabelReferences(
    referenceType: LabelReferenceType = LabelReferenceType.PARENT_LABEL
) = (this?.let { listOf(it) } ?: emptyList()).toLocalLabelReferences(referenceType)

// ====================
// Master
// ====================

fun LocalResourceAndMaster.toDomainMaster(): Master = Master(
    id = masterWithDetails.master.masterId,
    resourceUrl = resource.resourceUrl,
    uri = resource.uri,
    images = masterWithDetails.images.map { it.toDomainImage() },
    dataQuality = resource.dataQuality,
    title = masterWithDetails.master.title,
    genres = masterWithDetails.details.toDomainDetails(ResourceDetailType.GENRE),
    styles = masterWithDetails.details.toDomainDetails(ResourceDetailType.STYLE),
    year = masterWithDetails.master.year,
    numForSale = masterWithDetails.master.numForSale,
    lowestPrice = masterWithDetails.master.lowestPrice,
    trackList = masterWithDetails.trackList.unflattenToDomainTracks(),
    artists = masterWithDetails.artists.map { it.toDomainCreditReference() },
    videos = masterWithDetails.videos.map { it.toDomainVideo() },
    mainRelease = masterWithDetails.master.mainRelease,
    mostRecentRelease = masterWithDetails.master.mostRecentRelease,
    versionsUrl = masterWithDetails.master.versionsUrl,
    mainReleaseUrl = masterWithDetails.master.mainReleaseUrl,
    mostRecentReleaseUrl = masterWithDetails.master.mostRecentReleaseUrl
)

fun RemoteMaster.toLocalResourceAndMaster(): LocalResourceAndMaster = LocalResourceAndMaster(
    resource = toLocalResource(),
    masterWithDetails = toLocalMasterWithDetails()
)

fun RemoteMaster.toLocalMaster(): LocalMaster = LocalMaster(
    masterId = id,
    title = title,
    year = year,
    numForSale = numForSale,
    lowestPrice = lowestPrice,
    mainRelease = mainRelease,
    mostRecentRelease = mostRecentRelease,
    versionsUrl = versionsUrl,
    mainReleaseUrl = mainReleaseUrl,
    mostRecentReleaseUrl = mostRecentReleaseUrl
)

fun RemoteMaster.toLocalMasterWithDetails(): LocalMasterWithDetails = LocalMasterWithDetails(
    master = toLocalMaster(),
    images = images.mapIndexed { index, image -> image.toLocalImage(index) },
    details = genres.toLocalResourceDetails(ResourceDetailType.GENRE) +
            styles.toLocalResourceDetails(ResourceDetailType.STYLE),
    trackList = trackList.flattenToLocalTracksWithDetails(),
    artists = artists.map { it.toLocalCreditReference() },
    videos = videos.map { it.toLocalVideo() },
)

// ====================
// Credit reference
// ====================

fun RemoteCreditReference.toLocalCreditReference(): LocalCreditReference = LocalCreditReference(
    artistId = id,
    name = name,
    anv = anv,
    join = join,
    role = role,
    tracks = tracks,
    resourceUrl = resourceUrl,
    thumbnailUrl = thumbnailUrl
)

fun LocalCreditReference.toDomainCreditReference(): CreditReference = CreditReference(
    id = artistId,
    name = name,
    resourceUrl = resourceUrl,
    anv = anv,
    join = join,
    role = role,
    tracks = tracks,
    thumbnailUrl = thumbnailUrl
)

// ====================
// Track
// ====================

fun RemoteTrack.toLocalTrack(
    trackNum: Int = 0,
    parentTrackNum: Int = -1
): LocalTrack = LocalTrack(
    trackNum = trackNum,
    parentTrackNum = parentTrackNum,
    position = position,
    type = type,
    title = title,
    duration = duration.toElapsedTimeString(DurationUnit.MINUTES)
)

fun RemoteTrack.toLocalTrackWithDetails(
    trackNum: Int = 0,
    parentTrackNum: Int = -1
): LocalTrackWithDetails = LocalTrackWithDetails(
    track = toLocalTrack(trackNum, parentTrackNum),
    extraArtists = extraArtists?.map { it.toLocalCreditReference() }
)

fun LocalTrackWithDetails.toDomainTrack(): Track = Track(
    position = track.position,
    type = track.type,
    title = track.title,
    extraArtists = extraArtists?.map { it.toDomainCreditReference() },
    duration = parseElapsedTimeString(track.duration)
)

fun List<RemoteTrack>.flattenToLocalTracksWithDetails(): List<LocalTrackWithDetails> {
    // Use stack to non-recursively walk through tracks
    // See https://stackoverflow.com/questions/5987867/traversing-a-n-ary-tree-without-using-recurrsion
    var lastUsedTrackNum = -1
    val flattened = mutableListOf<LocalTrackWithDetails>()
    val stack = Stack<Pair<RemoteTrack, Int>>()
    fastForEachReversed { stack.push(Pair(it, lastUsedTrackNum)) }
    while (stack.isNotEmpty()) {
        val pair = stack.pop()
        val track = pair.first
        val trackNum = ++lastUsedTrackNum
        val parentTrackNum = pair.second
        val localTrack = track.toLocalTrackWithDetails(trackNum, parentTrackNum)
        track.subTracks?.fastForEachReversed { stack.push(Pair(it, trackNum)) }
        flattened.add(localTrack)
    }
    return flattened.toList()
}

@Suppress("SpellCheckingInspection")
fun List<LocalTrackWithDetails>.unflattenToDomainTracks(): List<Track> {
    val trackMap = this.associateBy(
        keySelector = { it.track.trackNum }
    ) { it.toDomainTrack() }.toMutableMap()

    for (localTrackWithDetails in this) {
        val trackNum = localTrackWithDetails.track.trackNum
        val track = trackMap[trackNum] ?: continue

        val parentTrackNum = localTrackWithDetails.track.parentTrackNum
        val parentTrack = trackMap[parentTrackNum] ?: continue

        // We have a valid parent, so we need to add to its subTracks
        val subTracks = parentTrack.subTracks.orEmpty().toMutableList().apply { add(track) }
        trackMap.replace(parentTrackNum, parentTrack.copy(subTracks = subTracks))
    }

    return filter { it.track.parentTrackNum == -1 }
        .mapNotNull { trackMap[it.track.trackNum] }
}

// ====================
// Video
// ====================

fun RemoteVideo.toLocalVideo(): LocalVideo = LocalVideo(
    title = title,
    description = description,
    uri = uri,
    duration = duration,
    embed = embed
)

fun LocalVideo.toDomainVideo(): Video = Video(
    title = title,
    description = description,
    uri = uri,
    duration = duration,
    embed = embed
)

// endregion Methods
