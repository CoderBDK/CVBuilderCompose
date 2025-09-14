package com.coderbdk.cvbuilder.data.repository

import com.coderbdk.cvbuilder.data.model.CVLayout
import com.coderbdk.cvbuilder.data.model.CVPage
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.data.model.CVText
import com.coderbdk.cvbuilder.data.model.LayoutProperties
import com.coderbdk.cvbuilder.util.demoCVJsonTemplate
import com.coderbdk.cvbuilder.util.toCVTemplate
import cvbuilder.composeapp.generated.resources.Res
import cvbuilder.composeapp.generated.resources.demo_template


class TemplateRepositoryImpl : TemplateRepository {
    override fun getCVTemplates(): List<CVTemplate> {
        return listOf(
            CVTemplate(
                name = "New Template",
                pages = listOf(
                    CVPage(
                        components = listOf(
                            CVLayout(
                                layoutProperties = LayoutProperties(
                                    width = 1,
                                    height = 1
                                ),
                                children = listOf(
                                    CVText("Text")
                                )
                            )
                        )
                    )
                )
            ),
            // demoCVJson.toCVTemplate(),
            demoCVJsonTemplate.toCVTemplate().copy(
                name = "Demo Template",
                imageResThumb = Res.drawable.demo_template
            )
        )
    }

}