package com.dicoding.mystory.ui

import com.dicoding.mystory.data.database.Story

object DataDummy {
    fun generateDummyStoryResponse(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                i.toString(),
                "photoUrl story $i",
                "name of user story $i",
                "description story $i"
            )
            items.add(story)
        }
        return items
    }
}