package com.joao01sb.mercadolibreapp.data.remote.utils

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.FileNotFoundException
import java.io.IOException

class AssetFileReaderTest {

    private lateinit var mockContext: Context
    private lateinit var mockAssetManager: AssetManager
    private lateinit var assetFileReader: AssetFileReader

    @Before
    fun setUp() {
        mockContext = mockk(relaxed = true)
        mockAssetManager = mockk(relaxed = true)
        every { mockContext.assets } returns mockAssetManager

        assetFileReader = AssetFileReader(mockContext)

        mockkStatic(Log::class)
        every { Log.e(any(), any<String>()) } returns 0
        every { Log.e(any(), any<String>(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `readFile should return content when file exists and has content`() {
        val fileName = "search-MLA-iphone.json"
        val expectedContent = """{"query": "iphone", "results": []}"""
        val inputStream = ByteArrayInputStream(expectedContent.toByteArray())

        every { mockAssetManager.open(fileName) } returns inputStream

        val result = assetFileReader.readFile(fileName)

        assertEquals(expectedContent, result)
    }

    @Test(expected = FileNotFoundException::class)
    fun `readFile should throw FileNotFoundException when file does not exist`() {
        val fileName = "nonexistent-file.json"
        every { mockAssetManager.open(fileName) } throws FileNotFoundException()

        try {
            assetFileReader.readFile(fileName)
        } catch (e: FileNotFoundException) {
            assertEquals("Asset file not found: $fileName", e.message)
            verify { Log.e("AssetFileReader", "Asset file not found: $fileName", any()) }
            throw e
        }
    }

    @Test(expected = IOException::class)
    fun `readFile should throw IOException when file is empty`() {
        val fileName = "empty-file.json"
        val emptyInputStream = ByteArrayInputStream("".toByteArray())

        every { mockAssetManager.open(fileName) } returns emptyInputStream

        try {
            assetFileReader.readFile(fileName)
        } catch (e: IOException) {
            assertEquals("Failed to read asset file: $fileName", e.message)
            verify { Log.e("AssetFileReader", "Failed to read asset file: $fileName", any()) }
            throw e
        }
    }

    @Test
    fun `buildSearchFileName should return correctly formatted filename`() {
        val query = "iPhone 13 Pro"
        val expectedFileName = "searches/search-MLA-iphone 13 pro.json"

        val result = assetFileReader.buildSearchFileName(query)

        assertEquals(expectedFileName, result)
    }
}
