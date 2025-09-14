package com.coderbdk.cvbuilder.util

import org.w3c.files.Blob

external fun createPdf(width: Int, height: Int): JsAny
external fun addImage(doc: JsAny, dataUrl: String, x: Float, y: Float, width: Float, height: Float)
external fun addPage(doc: JsAny)
external fun outputBlob(doc: JsAny): Blob
