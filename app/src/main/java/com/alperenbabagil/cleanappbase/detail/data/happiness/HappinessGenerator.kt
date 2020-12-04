package com.alperenbabagil.cleanappbase.detail.data.happiness

import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import kotlinx.coroutines.delay
import kotlin.random.Random

class HappinessGenerator(private val happinessProducer: HappinessProducer) {

    var isGeneratingOn = true

    suspend fun generate(profileDetail: ProfileDetail,callback : (percentage:Int) -> Unit){
        while (isGeneratingOn){
            delay(1500)
            if(isGeneratingOn) callback.invoke(happinessProducer
                    .produce(profileDetail.username ?: "John Doge"))
        }
    }
}