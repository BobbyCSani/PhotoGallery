package com.photo.gallery

import com.photo.gallery.data.mapper.toPhotoUIModel
import com.photo.gallery.utility.ddMMMyyyy
import org.junit.Assert
import org.junit.Test

class PhotoModelMapperTest {

    @Test
    fun photoResponseToPhotoUIModel(){
        val actual = generatePhotoResponseModel()
        val expected = actual.toPhotoUIModel()
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.description, actual.description)
        Assert.assertEquals(expected.username, actual.user?.name)
        Assert.assertEquals(expected.thumbnailUrl, actual.urls?.thumb)
        Assert.assertEquals(expected.smallUrl, actual.urls?.small)
        Assert.assertEquals(expected.date, actual.created_at.ddMMMyyyy())
    }
}