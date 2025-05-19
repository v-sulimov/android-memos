package com.vsulimov.memos.factory.middleware

import android.content.Context
import com.vsulimov.memos.factory.storage.PrivacyPolicyStorageFactory
import com.vsulimov.memos.middleware.PrivacyPolicyMiddleware

/**
 * A factory object responsible for creating instances of [PrivacyPolicyMiddleware].
 * This singleton provides a centralized way to instantiate the middleware with the required dependencies.
 */
object PrivacyPolicyMiddlewareFactory {
    /**
     * Creates a new instance of [PrivacyPolicyMiddleware] with a configured storage.
     *
     * @param context The Android [Context] used to initialize the storage for the privacy policy.
     * @return A configured [PrivacyPolicyMiddleware] instance.
     */
    fun createPrivacyPolicyMiddleware(context: Context): PrivacyPolicyMiddleware {
        val storage = PrivacyPolicyStorageFactory.createPrivacyPolicyStorage(context)
        return PrivacyPolicyMiddleware(storage)
    }
}
