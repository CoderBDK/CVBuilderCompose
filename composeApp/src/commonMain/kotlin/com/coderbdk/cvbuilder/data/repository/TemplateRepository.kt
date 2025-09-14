package com.coderbdk.cvbuilder.data.repository

import com.coderbdk.cvbuilder.data.model.CVTemplate

interface TemplateRepository {
    fun getCVTemplates(): List<CVTemplate>
}