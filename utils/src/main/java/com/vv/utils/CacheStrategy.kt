package com.vv.utils

interface DataCacheStrategy

sealed interface CacheStrategy : DataCacheStrategy {
    object Any : CacheStrategy
    object Memory : CacheStrategy
    object Remote : CacheStrategy
}
