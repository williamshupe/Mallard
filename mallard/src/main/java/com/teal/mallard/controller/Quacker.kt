package com.teal.mallard.controller

import android.util.Log
import com.teal.mallard.model.Quack
import com.teal.mallard.model.QuackErrorResponse
import com.teal.mallard.model.QuackResponse
import okhttp3.*
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by kodyvanry on 8/16/17
 *
 * <p>
 *     Quacker is a class purely meant for conversing between a WebDav server and client
 * </p>
 */
class Quacker {

    val quackQueue = LinkedList<WeakReference<Quack>>()

    var conversationThread: Thread? = null

    /**
     * Begins the conversation. If the conversation thread has died it starts it back up again and
     * continues the conversation
     */
    fun beginConversation() {
        conversationThread = conversationThread ?: thread(false, true, block = { converse() })
        conversationThread?.let {
            if (!it.isAlive) {
                it.start()
            }
        }
    }

    /**
     * This is where the conversation between WebDav and client actually happens.
     *
     * THIS NEEDS TO STAY HIDDEN
     */
    private fun converse() {
        while(quackQueue.size > 0) {
            val currentQuack = quackQueue.removeFirst().get() ?: return

            val client = OkHttpClient()
            Log.d(this::class.java.simpleName, currentQuack.getRequestBody())
            val requestBuilder = Request.Builder()
                    .url(HttpUrl.parse(currentQuack.url))
                    .method(currentQuack.method, RequestBody.create(MediaType.parse(currentQuack.getContentType()), currentQuack.getRequestBody()))
            requestBuilder.let {
                currentQuack.headers.entries.forEach { entry ->
                    it.header(entry.key, entry.value)
                }
            }

            val request = requestBuilder.build()

            try {
                // Read the response from the request
                val response = QuackResponse(currentQuack.requestCode, client.newCall(request).execute())

                currentQuack.onResponseListener?.onResponse(response)
            } catch (e: IOException) {
                e.printStackTrace()
                currentQuack.onErrorListener?.onError(QuackErrorResponse(currentQuack.requestCode, e))
            }

        }
    }

    /**
     * This is how you send out a quack and wait for a response.
     * Will the other duck really hear you!?! Let's hope so :)
     *
     * @param quack Let your voice be heard, tell Mallard what you want!
     */
    fun quack(quack: Quack) {
        beginConversation()
        quackQueue += WeakReference<Quack>(quack)
    }

}