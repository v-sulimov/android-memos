package com.vsulimov.memos.factory.middleware

import android.content.Context
import com.vsulimov.memos.factory.storage.PrivacyPolicyStorageFactory
import com.vsulimov.memos.middleware.OnboardingMiddleware

/**
 * A factory object responsible for creating instances of [OnboardingMiddleware].
 * This factory provides a centralized way to instantiate [OnboardingMiddleware] with the necessary
 * dependencies, ensuring proper configuration for the onboarding process.
 */
object OnboardingMiddlewareFactory {
    /**
     * Creates an instance of [OnboardingMiddleware] with a [PrivacyPolicyStorage] configured for the given context.
     *
     * @param context The Android [Context] used to initialize the [PrivacyPolicyStorage].
     * @return A configured [OnboardingMiddleware] instance ready to handle [OnboardingAction.GetStarted] actions.
     */
    fun createOnboardingMiddleware(context: Context): OnboardingMiddleware {
        val storage = PrivacyPolicyStorageFactory.createPrivacyPolicyStorage(context)
        return OnboardingMiddleware(storage)
    }
}
