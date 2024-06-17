package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelDetail
import com.codepunk.skeleton.data.local.entity.LocalSubLabel
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

private fun List<RemoteLabel.SubLabel>.toLocalSubLabels(
    id: Long
): List<LocalSubLabel> = mapIndexed { subLabelIdx, subLabel ->
    LocalSubLabel(
        parentId = id,
        subLabelIdx = subLabelIdx,
        childId = subLabel.id,
        name = subLabel.name,
        resourceUrl = subLabel.resourceUrl
    )
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

private fun List<LocalSubLabel>.toDomainLabelSubLabels(): List<Label.SubLabel> =
    map { subLabel ->
        Label.SubLabel(
            id = subLabel.childId,
            name = subLabel.name,
            resourceUrl = subLabel.resourceUrl
        )
    }

// endregion Methods