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
            id = this.id,
            name = this.name,
            resourceUrl = this.resourceUrl,
            uri = this.uri,
            releasesUrl = this.releasesUrl,
            contactInfo = this.contactInfo,
            profile = this.profile,
            dataQuality = this.dataQuality
        ),
        images = this.images.map { it.toLocalImage() },
        details = this.urls.toLocalLabelDetails(this.id, EntityDetailType.URL),
        subLabels = this.subLabels.toLocalSubLabels(this.id)
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

private fun RemoteLabel.Relationship.toLocalLabelRelationship(
    parentId: Long,
    relationshipIdx: Int = 0
): LocalLabelRelationship = LocalLabelRelationship(
    parentId = parentId,
    relationshipIdx = relationshipIdx,
    childId = this.id,
    name = this.name,
    resourceUrl = this.resourceUrl
)

private fun List<RemoteLabel.Relationship>.toLocalSubLabels(
    parentId: Long
): List<LocalLabelRelationship> = mapIndexed { subLabelIdx, subLabel ->
    subLabel.toLocalLabelRelationship(parentId, subLabelIdx)
}

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
    subLabels = subLabels.toDomainLabelSubLabels()
)

private fun List<LocalLabelDetail>.toDomainLabelDetails(
    detailType: EntityDetailType
): List<String> = filter { detail ->
    detail.detailType == detailType
}.map { it.detail }

private fun List<LocalLabelRelationship>.toDomainLabelSubLabels(): List<Label.Relationship> =
    map { subLabel ->
        Label.Relationship(
            id = subLabel.childId,
            name = subLabel.name,
            resourceUrl = subLabel.resourceUrl
        )
    }

// endregion Methods
