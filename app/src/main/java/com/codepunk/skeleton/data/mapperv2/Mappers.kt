package com.codepunk.skeleton.data.mapperv2

import com.codepunk.skeleton.data.local.type.ArtistReferenceType
import com.codepunk.skeleton.data.local.type.LabelReferenceType
import com.codepunk.skeleton.data.local.type.ResourceDetailType
import com.codepunk.skeleton.data.localv2.entity.LocalArtist
import com.codepunk.skeleton.data.localv2.entity.LocalArtistReference
import com.codepunk.skeleton.data.localv2.entity.LocalImage
import com.codepunk.skeleton.data.localv2.entity.LocalLabel
import com.codepunk.skeleton.data.localv2.entity.LocalLabelReference
import com.codepunk.skeleton.data.localv2.entity.LocalResource
import com.codepunk.skeleton.data.localv2.entity.LocalResourceDetail
import com.codepunk.skeleton.data.localv2.relation.LocalArtistWithDetails
import com.codepunk.skeleton.data.localv2.relation.LocalLabelWithDetails
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndArtist
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndLabel
import com.codepunk.skeleton.data.remotev2.entity.RemoteArtist
import com.codepunk.skeleton.data.remotev2.entity.RemoteArtistReference
import com.codepunk.skeleton.data.remotev2.entity.RemoteImage
import com.codepunk.skeleton.data.remotev2.entity.RemoteLabel
import com.codepunk.skeleton.data.remotev2.entity.RemoteLabelReference
import com.codepunk.skeleton.data.remotev2.entity.RemoteResource
import com.codepunk.skeleton.domainv2.model.Artist
import com.codepunk.skeleton.domainv2.model.ArtistReference
import com.codepunk.skeleton.domainv2.model.Image
import com.codepunk.skeleton.domainv2.model.Label
import com.codepunk.skeleton.domainv2.model.LabelReference

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

// endregion Methods
