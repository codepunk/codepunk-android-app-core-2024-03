package com.codepunk.skeleton.data.mapper

import androidx.compose.ui.util.fastForEachReversed
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalRelatedArtist
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDescription
import com.codepunk.skeleton.data.local.entity.LocalIdentifier
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalRelatedLabel
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalResource
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.entity.LocalVideo
import com.codepunk.skeleton.data.local.relation.LocalArtistWithDetails
import com.codepunk.skeleton.data.local.relation.LocalFormatWithDescriptions
import com.codepunk.skeleton.data.local.relation.LocalLabelWithDetails
import com.codepunk.skeleton.data.local.relation.LocalMasterWithDetails
import com.codepunk.skeleton.data.local.relation.LocalReleaseWithDetails
import com.codepunk.skeleton.data.local.relation.LocalResourceAndArtist
import com.codepunk.skeleton.data.local.relation.LocalResourceAndLabel
import com.codepunk.skeleton.data.local.relation.LocalResourceAndMaster
import com.codepunk.skeleton.data.local.relation.LocalResourceAndRelease
import com.codepunk.skeleton.data.local.relation.LocalTrackWithDetails
import com.codepunk.skeleton.data.remote.entity.RemoteArtist
import com.codepunk.skeleton.data.remote.entity.RemoteRelatedArtist
import com.codepunk.skeleton.data.remote.entity.RemoteCredit
import com.codepunk.skeleton.data.remote.entity.RemoteFormat
import com.codepunk.skeleton.data.remote.entity.RemoteIdentifier
import com.codepunk.skeleton.data.remote.entity.RemoteImage
import com.codepunk.skeleton.data.remote.entity.RemoteLabel
import com.codepunk.skeleton.data.remote.entity.RemoteRelatedLabel
import com.codepunk.skeleton.data.remote.entity.RemoteMaster
import com.codepunk.skeleton.data.remote.entity.RemoteRelease
import com.codepunk.skeleton.data.remote.entity.RemoteResource
import com.codepunk.skeleton.data.remote.entity.RemoteTrack
import com.codepunk.skeleton.data.remote.entity.RemoteVideo
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.RelatedArtist
import com.codepunk.skeleton.domain.model.Credit
import com.codepunk.skeleton.domain.model.Format
import com.codepunk.skeleton.domain.model.Identifier
import com.codepunk.skeleton.domain.model.Image
import com.codepunk.skeleton.domain.model.Label
import com.codepunk.skeleton.domain.model.RelatedLabel
import com.codepunk.skeleton.domain.model.Master
import com.codepunk.skeleton.domain.model.Release
import com.codepunk.skeleton.domain.model.Track
import com.codepunk.skeleton.domain.model.Video
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
    detailType: LocalResourceDetail.DetailType
): List<String> = filter { it.detailType == detailType }
    .sortedBy { it.detailIdx }
    .map { it.detail }

fun List<String>.toLocalResourceDetails(
    detailType: LocalResourceDetail.DetailType
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
    urls = artistWithDetails.details.toDomainDetails(LocalResourceDetail.DetailType.URL),
    realName = artistWithDetails.artist.realName,
    nameVariations = artistWithDetails.details.toDomainDetails(LocalResourceDetail.DetailType.NAME_VARIATION),
    aliases = artistWithDetails.relatedArtists.toDomainRelatedArtists(LocalRelatedArtist.RelationType.ALIAS),
    members = artistWithDetails.relatedArtists.toDomainRelatedArtists(LocalRelatedArtist.RelationType.MEMBER),
    groups = artistWithDetails.relatedArtists.toDomainRelatedArtists(LocalRelatedArtist.RelationType.GROUP)
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
        details = urls.toLocalResourceDetails(LocalResourceDetail.DetailType.URL) +
                nameVariations.toLocalResourceDetails(LocalResourceDetail.DetailType.NAME_VARIATION),
        relatedArtists = aliases.toLocalRelatedArtists(LocalRelatedArtist.RelationType.ALIAS) +
                members.toLocalRelatedArtists(LocalRelatedArtist.RelationType.MEMBER) +
                groups.toLocalRelatedArtists(LocalRelatedArtist.RelationType.GROUP)
    )

// ====================
// Artist reference
// ====================

fun LocalRelatedArtist.toDomainRelatedArtist() = RelatedArtist(
    id = artistId,
    name = name,
    resourceUrl = resourceUrl,
    active = active,
    thumbnailUrl = thumbnailUrl
)

fun List<LocalRelatedArtist>.toDomainRelatedArtists(
    relationType: LocalRelatedArtist.RelationType
): List<RelatedArtist> = filter { it.relationType == relationType }
    .map { it.toDomainRelatedArtist() }

fun List<RemoteRelatedArtist>.toLocalRelatedArtists(
    relationType: LocalRelatedArtist.RelationType
) = map { ref ->
    LocalRelatedArtist(
        relationType = relationType,
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
    urls = labelWithDetails.details.toDomainDetails(LocalResourceDetail.DetailType.URL),
    contactInfo = labelWithDetails.label.contactInfo,
    parentLabel = labelWithDetails.relatedLabels.toDomainRelatedLabels(
        LocalRelatedLabel.RelationType.PARENT_LABEL
    ).firstOrNull(),
    subLabels = labelWithDetails.relatedLabels.toDomainRelatedLabels(LocalRelatedLabel.RelationType.SUB_LABEL)
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
        details = urls.toLocalResourceDetails(LocalResourceDetail.DetailType.URL),
        relatedLabels = parentLabel.toLocalRelatedLabels() +
                subLabels.toLocalRelatedLabels(LocalRelatedLabel.RelationType.SUB_LABEL)
    )

// ====================
// Label reference
// ====================

fun LocalRelatedLabel.toDomainRelatedLabel() = RelatedLabel(
    id = labelId,
    name = name,
    resourceUrl = resourceUrl
)

fun List<LocalRelatedLabel>.toDomainRelatedLabels(
    relationType: LocalRelatedLabel.RelationType
): List<RelatedLabel> = filter { it.referenceType == relationType }
    .map { it.toDomainRelatedLabel() }

fun List<RemoteRelatedLabel>.toLocalRelatedLabels(
    relationType: LocalRelatedLabel.RelationType
) = map { ref ->
    LocalRelatedLabel(
        referenceType = relationType,
        labelId = ref.id,
        name = ref.name,
        resourceUrl = ref.resourceUrl
    )
}

// TODO Is there a way to avoid parentLabel being a list?
fun RemoteRelatedLabel?.toLocalRelatedLabels(
    relationType: LocalRelatedLabel.RelationType = LocalRelatedLabel.RelationType.PARENT_LABEL
) = (this?.let { listOf(it) } ?: emptyList()).toLocalRelatedLabels(relationType)

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
    genres = masterWithDetails.details.toDomainDetails(LocalResourceDetail.DetailType.GENRE),
    styles = masterWithDetails.details.toDomainDetails(LocalResourceDetail.DetailType.STYLE),
    year = masterWithDetails.master.year,
    numForSale = masterWithDetails.master.numForSale,
    lowestPrice = masterWithDetails.master.lowestPrice,
    trackList = masterWithDetails.trackList.unflattenToDomainTracks(),
    artists = masterWithDetails.artists.toDomainCredit(LocalCredit.CreditType.ARTIST),
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
    details = genres.toLocalResourceDetails(LocalResourceDetail.DetailType.GENRE) +
            styles.toLocalResourceDetails(LocalResourceDetail.DetailType.STYLE),
    trackList = trackList.flattenToLocalTracksWithDetails(),
    artists = artists.toLocalCredits(LocalCredit.CreditType.ARTIST),
    videos = videos.map { it.toLocalVideo() },
)

// ====================
// Release
// ====================

fun LocalResourceAndRelease.toDomainRelease(): Release = Release(
    id = releaseWithDetails.release.releaseId,
    resourceUrl = resource.resourceUrl,
    uri = resource.uri,
    images = releaseWithDetails.images.map { it.toDomainImage() },
    dataQuality = resource.dataQuality,
    title = releaseWithDetails.release.title,
    genres = releaseWithDetails.details.toDomainDetails(LocalResourceDetail.DetailType.GENRE),
    styles = releaseWithDetails.details.toDomainDetails(LocalResourceDetail.DetailType.STYLE),
    year = releaseWithDetails.release.year,
    numForSale = releaseWithDetails.release.numForSale,
    lowestPrice = releaseWithDetails.release.lowestPrice,
    trackList = releaseWithDetails.trackList.unflattenToDomainTracks(),
    artists = releaseWithDetails.relatedArtists.toDomainCredit(LocalCredit.CreditType.ARTIST),
    videos = releaseWithDetails.videos.map { it.toDomainVideo() },
    status = releaseWithDetails.release.status,
    artistsSort = releaseWithDetails.release.artistsSort,
    labels = releaseWithDetails.relatedLabels.toDomainRelatedLabels(LocalRelatedLabel.RelationType.LABEL),
    series = releaseWithDetails.relatedLabels.toDomainRelatedLabels(LocalRelatedLabel.RelationType.SERIES),
    companies = releaseWithDetails.relatedLabels.toDomainRelatedLabels(LocalRelatedLabel.RelationType.COMPANY),
    formats = releaseWithDetails.formats.map { it.toDomainFormat() },
    formatQuantity = releaseWithDetails.release.formatQuantity,
    dateAdded = releaseWithDetails.release.dateAdded,
    dateChanged = releaseWithDetails.release.dateChanged,
    masterId = releaseWithDetails.release.masterId,
    masterUrl = releaseWithDetails.release.masterUrl,
    country = releaseWithDetails.release.country,
    released = releaseWithDetails.release.released,
    notes = releaseWithDetails.release.notes,
    releasedFormatted = releaseWithDetails.release.releasedFormatted,
    identifiers = releaseWithDetails.identifiers.map { it.toDomainIdentifier() },
    extraArtists = releaseWithDetails.relatedArtists.toDomainCredit(LocalCredit.CreditType.EXTRA_ARTIST),
    thumb = releaseWithDetails.release.thumb
)

fun RemoteRelease.toLocalResourceAndRelease(): LocalResourceAndRelease = LocalResourceAndRelease(
    resource = toLocalResource(),
    releaseWithDetails = toLocalReleaseWithDetails()
)

fun RemoteRelease.toLocalRelease(): LocalRelease = LocalRelease(
    releaseId = id,
    title = title,
    year = year,
    numForSale = numForSale,
    lowestPrice = lowestPrice,
    status = status,
    artistsSort = artistsSort,
    formatQuantity = formatQuantity,
    dateAdded = dateAdded,
    dateChanged = dateChanged,
    masterId = masterId,
    masterUrl = masterUrl,
    country = country,
    released = released,
    notes = notes,
    releasedFormatted = releasedFormatted,
    thumb = thumb
)

fun RemoteRelease.toLocalReleaseWithDetails(): LocalReleaseWithDetails = LocalReleaseWithDetails(
    release = toLocalRelease(),
    images = images.mapIndexed { index, image -> image.toLocalImage(index) },
    details = genres.toLocalResourceDetails(LocalResourceDetail.DetailType.GENRE) +
            styles.toLocalResourceDetails(LocalResourceDetail.DetailType.STYLE),
    trackList = trackList.flattenToLocalTracksWithDetails(),
    relatedArtists = artists.toLocalCredits(LocalCredit.CreditType.ARTIST) +
            extraArtists.toLocalCredits(LocalCredit.CreditType.EXTRA_ARTIST),
    videos = videos.map { it.toLocalVideo() },
    relatedLabels = labels.toLocalRelatedLabels(LocalRelatedLabel.RelationType.LABEL) +
            series.toLocalRelatedLabels(LocalRelatedLabel.RelationType.SERIES) +
            companies.toLocalRelatedLabels(LocalRelatedLabel.RelationType.COMPANY),
    formats = formats.toLocalFormatsWithDescriptions(),
    identifiers = identifiers.toLocalIdentifiers()
)

// ====================
// Credit reference
// ====================

fun List<RemoteCredit>.toLocalCredits(
    type: LocalCredit.CreditType
): List<LocalCredit> = map { ref ->
    LocalCredit(
        type = type,
        artistId = ref.id,
        name = ref.name,
        anv = ref.anv,
        join = ref.join,
        role = ref.role,
        tracks = ref.tracks,
        resourceUrl = ref.resourceUrl,
        thumbnailUrl = ref.thumbnailUrl
    )
}

fun LocalCredit.toDomainCredit(): Credit = Credit(
    id = artistId,
    name = name,
    resourceUrl = resourceUrl,
    anv = anv,
    join = join,
    role = role,
    tracks = tracks,
    thumbnailUrl = thumbnailUrl
)

fun List<LocalCredit>.toDomainCredit(
    type: LocalCredit.CreditType
): List<Credit> = filter { it.type == type }
    .map { it.toDomainCredit() }

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
    extraArtists = extraArtists?.toLocalCredits(LocalCredit.CreditType.EXTRA_ARTIST)
)

fun LocalTrackWithDetails.toDomainTrack(): Track = Track(
    position = track.position,
    type = track.type,
    title = track.title,
    extraArtists = extraArtists?.map { it.toDomainCredit() },
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

// ====================
// Format
// ====================

fun List<RemoteFormat>.toLocalFormatsWithDescriptions(): List<LocalFormatWithDescriptions> =
    mapIndexed { formatIdx, remoteFormat ->
        LocalFormatWithDescriptions(
            format = LocalFormat(
                formatIdx = formatIdx,
                name = remoteFormat.name,
                quantity = remoteFormat.quantity,
                text = remoteFormat.text
            ),
            descriptions = remoteFormat.descriptions.mapIndexed { descriptionIdx, description ->
                LocalFormatDescription(
                    descriptionIdx = descriptionIdx,
                    description = description

                )
            }
        )
    }

fun LocalFormatWithDescriptions.toDomainFormat(): Format = Format(
    name = format.name,
    quantity = format.quantity,
    text = format.text,
    descriptions = descriptions.map { it.description }
)

// ====================
// Identifier
// ====================

fun List<RemoteIdentifier>.toLocalIdentifiers(): List<LocalIdentifier> =
    mapIndexed { index, remoteIdentifier ->
        LocalIdentifier(
            identifierIdx = index,
            type = remoteIdentifier.type,
            value = remoteIdentifier.value,
            description = remoteIdentifier.description
        )
    }

fun LocalIdentifier.toDomainIdentifier(): Identifier = Identifier(
    type = type,
    value = value,
    description = description
)

// endregion Methods
