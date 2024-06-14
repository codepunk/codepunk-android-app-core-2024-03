package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.remote.entity.RemotePagination
import com.codepunk.skeleton.domain.model.Pagination

fun RemotePagination.Urls.toDomain(): Pagination.Urls = Pagination.Urls(
    last = this.last,
    next = this.next
)

fun RemotePagination.toDomain(): Pagination = Pagination(
    page = this.page,
    pages = this.pages,
    perPage = this.perPage,
    items = this.items,
    urls = this.urls.toDomain()
)
