package com.alperenbabagil.cabdata

import com.alperenbabagil.dataholder.DataHolder


interface DataSource {

    interface AsyncDataSource : DataSource {
        interface RequestDataSource<Req, Res : Any> : DataSource {
            suspend fun getResult(request: Req): com.alperenbabagil.dataholder.DataHolder<Res>
        }

        interface FetchDataSource<Res : Any> : DataSource {
            suspend fun fetch(): com.alperenbabagil.dataholder.DataHolder<Res>
        }
    }


    interface SyncDataSource : DataSource {

        interface FetchSyncDataSource<Res : Any> : DataSource {
            fun fetch(): com.alperenbabagil.dataholder.DataHolder<Res>
        }
        interface RequestSyncDataSource<Req, Res : Any> : DataSource {
            fun getResult(request: Req): com.alperenbabagil.dataholder.DataHolder<Res>
        }
    }

}