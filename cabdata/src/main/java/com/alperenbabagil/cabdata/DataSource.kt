package com.alperenbabagil.cabdata

import com.alperenbabagil.cabdomain.model.DataHolder


interface DataSource {

    interface AsyncDataSource : DataSource {
        interface RequestDataSource<Req, Res : Any> : DataSource {
            suspend fun getResult(request: Req): DataHolder<Res>
        }

        interface FetchDataSource<Res : Any> : DataSource {
            suspend fun fetch(): DataHolder<Res>
        }
    }


    interface SyncDataSource : DataSource {

        interface FetchSyncDataSource<Res : Any> : DataSource {
            fun fetch(): DataHolder<Res>
        }
        interface RequestSyncDataSource<Req, Res : Any> : DataSource {
            fun getResult(request: Req): DataHolder<Res>
        }
    }

}