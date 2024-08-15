package com.codepunk.skeleton.ui.screen.product.master

sealed class MasterScreenEvent {

    data class LoadMaster(val masterId: Long) : MasterScreenEvent()

    data object RefreshMaster : MasterScreenEvent()

}