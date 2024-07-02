package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelDetail
import com.codepunk.skeleton.data.local.entity.LocalLabelRelationship
import com.codepunk.skeleton.data.local.relation.LocalLabelWithDetails
import com.codepunk.skeleton.data.local.type.EntityDetailType
import com.codepunk.skeleton.data.remote.entity.RemoteLabel
import com.codepunk.skeleton.domain.model.Label

// region Methods

fun RemoteLabel.toLocalLabelWithDetails(): LocalLabelWithDetails =
    LocalLabelWithDetails(
        label = LocalLabel(
            id = id,
            name = name,
            resourceUrl = resourceUrl,
            uri = uri,
            releasesUrl = releasesUrl,
            contactInfo = contactInfo,
            profile = profile,
            dataQuality = dataQuality
        ),
        images = images.map { it.toLocalImage() },
        details = urls.toLocalLabelDetails(id, EntityDetailType.URL),
        subLabels = subLabels.map { it.toLocalLabelRelationship() }
    )

private fun List<String>.toLocalLabelDetails(
    id: Long,
    detailType: EntityDetailType
): List<LocalLabelDetail> = mapIndexed { detailIdx, detail ->
    LocalLabelDetail(
        labelId = id,
        detailType = detailType,
        detailIdx = detailIdx,
        detail = detail
    )
}

private fun RemoteLabel.Relationship.toLocalLabelRelationship(): LocalLabelRelationship =
    LocalLabelRelationship(
        id = id,
        name = name,
        resourceUrl = resourceUrl
    )

fun LocalLabelWithDetails.toDomainLabel(): Label = Label(
    id = label.id,
    name = label.name,
    resourceUrl = label.resourceUrl,
    uri = label.uri,
    releasesUrl = label.releasesUrl,
    images = images.map { it.toDomainImage() },
    contactInfo = label.contactInfo,
    profile = label.profile,
    dataQuality = label.dataQuality,
    urls = details.toDomainLabelDetails(EntityDetailType.URL),
    subLabels = subLabels.map { it.toDomainLabelRelationship() }
)

private fun List<LocalLabelDetail>.toDomainLabelDetails(
    detailType: EntityDetailType
): List<String> = filter { detail ->
    detail.detailType == detailType
}.map { it.detail }

private fun LocalLabelRelationship.toDomainLabelRelationship(): Label.Relationship =
    Label.Relationship(
        id = id,
        name = name,
        resourceUrl = resourceUrl
    )

// endregion Methods
