package com.coderbdk.cvbuilder.util

import com.coderbdk.cvbuilder.data.model.CVDivider
import com.coderbdk.cvbuilder.data.model.CVLayout
import com.coderbdk.cvbuilder.data.model.CVPage
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.data.model.CVText

private val template = CVTemplate(
    pages = listOf(
        CVPage(
            components = listOf(
                CVLayout(
                    orientation = CVLayout.Orientation.Vertical,
                    children = listOf(
                        CVText(
                            text = "",
                            fontSize = 24,
                            textColor = "#000FFF",
                            textAlign = CVText.Align.End
                        ),
                        CVDivider(),
                        CVLayout(
                            orientation = CVLayout.Orientation.Horizontal,
                            children = listOf(
                                CVText(),
                                CVLayout(
                                    orientation = CVLayout.Orientation.Horizontal,
                                    children = listOf(
                                        CVText(),
                                        CVText(),
                                    )
                                )
                            )
                        )
                    )
                ),
            )
        )
    )
)

val demoCVJsonTemplate = """
    {
        "name": "New Template",
        "pageWidth": 1323,
        "pageHeight": 1870,
        "pages": [
            {
                "components": [
                    {
                        "type": "CVLayout",
                        "name": "Layout",
                        "layoutProperties": {
                            "width": 1,
                            "height": 1,
                            "weight": -1.0,
                            "background": "#00000000",
                            "paddingStart": 160,
                            "paddingTop": 160,
                            "paddingEnd": 160,
                            "paddingBottom": 160
                        },
                        "children": [
                            {
                                "type": "CVText",
                                "name": "Text",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 0,
                                    "paddingEnd": 0,
                                    "paddingBottom": 0
                                },
                                "text": "[Your Name]",
                                "fontSize": 32,
                                "textColor": "#FF000000",
                                "textAlign": "Unspecified",
                                "fontFamily": "Serif",
                                "bold": true
                            },
                            {
                                "type": "CVDivider",
                                "name": "Divider",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 8,
                                    "paddingEnd": 0,
                                    "paddingBottom": 24
                                },
                                "orientation": "Horizontal",
                                "thickness": 1.0,
                                "color": "#FF000000"
                            },
                            {
                                "type": "CVLayout",
                                "name": "Layout",
                                "layoutProperties": {
                                    "width": 1,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 0,
                                    "paddingEnd": 0,
                                    "paddingBottom": 0
                                },
                                "children": [
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "[Your Phone Number]",
                                        "fontSize": 20,
                                        "textColor": "#FF000000",
                                        "textAlign": "Unspecified",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    },
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": " [Your Email Address]",
                                        "fontSize": 20,
                                        "textColor": "#FF000000",
                                        "textAlign": "Unspecified",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    },
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": " [Your City, State]",
                                        "fontSize": 20,
                                        "textColor": "#FF000000",
                                        "textAlign": "Unspecified",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    },
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "[LinkedIn]",
                                        "fontSize": 20,
                                        "textColor": "#FF000000",
                                        "textAlign": "Unspecified",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    },
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "[Portfolio/GitHub]",
                                        "fontSize": 20,
                                        "textColor": "#FF000000",
                                        "textAlign": "Unspecified",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    }
                                ],
                                "orientation": "Horizontal",
                                "arrangement": "SpaceBetween",
                                "alignment": "Start"
                            },
                            {
                                "type": "CVLayout",
                                "name": "Layout",
                                "layoutProperties": {
                                    "width": 1,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 24,
                                    "paddingEnd": 0,
                                    "paddingBottom": 8
                                },
                                "children": [
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "Professional Summary",
                                        "fontSize": 22,
                                        "textColor": "#FF000000",
                                        "textAlign": "Unspecified",
                                        "fontFamily": "Serif",
                                        "bold": true
                                    },
                                    {
                                        "type": "CVDivider",
                                        "name": "Divider",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 8,
                                            "paddingEnd": 0,
                                            "paddingBottom": 8
                                        },
                                        "orientation": "Horizontal",
                                        "thickness": 1.0,
                                        "color": "#FF000000"
                                    }
                                ],
                                "orientation": "Vertical",
                                "arrangement": "Top",
                                "alignment": "Start"
                            },
                            {
                                "type": "CVText",
                                "name": "Text",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 0,
                                    "paddingEnd": 0,
                                    "paddingBottom": 0
                                },
                                "text": "A passionate and results-driven [Your Profession] with [Number] years of experience in [Your Industry]. Skilled in [Key Skill 1], [Key Skill 2], and [Key Skill 3]. Proven ability to [Your Key Achievement/Value Proposition, e.g., 'lead projects from concept to completion' or 'improve system efficiency by X%'].",
                                "fontSize": 16,
                                "textColor": "#FF000000",
                                "textAlign": "Justify",
                                "fontFamily": "Serif",
                                "bold": false
                            },
                            {
                                "type": "CVLayout",
                                "name": "Layout",
                                "layoutProperties": {
                                    "width": 1,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 24,
                                    "paddingEnd": 0,
                                    "paddingBottom": 8
                                },
                                "children": [
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "Work Experience",
                                        "fontSize": 22,
                                        "textColor": "#FF000000",
                                        "textAlign": "Unspecified",
                                        "fontFamily": "Serif",
                                        "bold": true
                                    },
                                    {
                                        "type": "CVDivider",
                                        "name": "Divider",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 8,
                                            "paddingEnd": 0,
                                            "paddingBottom": 8
                                        },
                                        "orientation": "Horizontal",
                                        "thickness": 1.0,
                                        "color": "#FF000000"
                                    }
                                ],
                                "orientation": "Vertical",
                                "arrangement": "Top",
                                "alignment": "Start"
                            },
                            {
                                "type": "CVText",
                                "name": "Text",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 0,
                                    "paddingEnd": 0,
                                    "paddingBottom": 0
                                },
                                "text": "[Job Title, e.g., Senior Software Engineer]",
                                "fontSize": 18,
                                "textColor": "#FF000000",
                                "textAlign": "Unspecified",
                                "fontFamily": "Serif",
                                "bold": true
                            },
                            {
                                "type": "CVLayout",
                                "name": "Layout",
                                "layoutProperties": {
                                    "width": 1,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 16,
                                    "paddingEnd": 0,
                                    "paddingBottom": 16
                                },
                                "children": [
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": 1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "[Company Name], [City, State]",
                                        "fontSize": 16,
                                        "textColor": "#FF000000",
                                        "textAlign": "Start",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    },
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": 1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": " [Start Date] - [End Date]",
                                        "fontSize": 16,
                                        "textColor": "#FF000000",
                                        "textAlign": "End",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    }
                                ],
                                "orientation": "Horizontal",
                                "arrangement": "Top",
                                "alignment": "Start"
                            },
                            {
                                "type": "CVList",
                                "name": "List",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 16,
                                    "paddingTop": 0,
                                    "paddingEnd": 16,
                                    "paddingBottom": 0
                                },
                                "items": [
                                    "[Action verb] [specific task or responsibility], resulting in [quantifiable achievement, e.g., 'improved page load time by 20%'].",
                                    "[Action verb] [project or initiative], leading to [positive outcome, e.g., 'launched a new feature used by over 10,000 users'].",
                                    "[Action verb] [daily responsibility], using [relevant technology or skill]."
                                ],
                                "fontSize": 16,
                                "textColor": "#FF000000",
                                "textAlign": "Justify",
                                "fontFamily": "Serif",
                                "style": "Bullet",
                                "bold": false
                            },
                            {
                                "type": "CVText",
                                "name": "Text",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 16,
                                    "paddingEnd": 0,
                                    "paddingBottom": 0
                                },
                                "text": "[Job Title, e.g., Junior Developer]",
                                "fontSize": 18,
                                "textColor": "#FF000000",
                                "textAlign": "Unspecified",
                                "fontFamily": "Serif",
                                "bold": true
                            },
                            {
                                "type": "CVLayout",
                                "name": "Layout",
                                "layoutProperties": {
                                    "width": 1,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 16,
                                    "paddingEnd": 0,
                                    "paddingBottom": 16
                                },
                                "children": [
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": 1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "[Previous Company Name], [City, State]",
                                        "fontSize": 16,
                                        "textColor": "#FF000000",
                                        "textAlign": "Start",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    },
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": 1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": " [Start Date] - [End Date]",
                                        "fontSize": 16,
                                        "textColor": "#FF000000",
                                        "textAlign": "End",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    }
                                ],
                                "orientation": "Horizontal",
                                "arrangement": "Top",
                                "alignment": "Start"
                            },
                            {
                                "type": "CVList",
                                "name": "List",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 16,
                                    "paddingTop": 0,
                                    "paddingEnd": 16,
                                    "paddingBottom": 0
                                },
                                "items": [
                                    "[Action verb] [specific task or responsibility], resulting in [quantifiable achievement, e.g., 'reduced bug reports by 15%'].",
                                    "[Action verb] [project or initiative], leading to [positive outcome]."
                                ],
                                "fontSize": 16,
                                "textColor": "#FF000000",
                                "textAlign": "Justify",
                                "fontFamily": "Serif",
                                "style": "Bullet",
                                "bold": false
                            },
                            {
                                "type": "CVLayout",
                                "name": "Layout",
                                "layoutProperties": {
                                    "width": 1,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 24,
                                    "paddingEnd": 0,
                                    "paddingBottom": 8
                                },
                                "children": [
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "Education",
                                        "fontSize": 22,
                                        "textColor": "#FF000000",
                                        "textAlign": "Unspecified",
                                        "fontFamily": "Serif",
                                        "bold": true
                                    },
                                    {
                                        "type": "CVDivider",
                                        "name": "Divider",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 8,
                                            "paddingEnd": 0,
                                            "paddingBottom": 8
                                        },
                                        "orientation": "Horizontal",
                                        "thickness": 1.0,
                                        "color": "#FF000000"
                                    }
                                ],
                                "orientation": "Vertical",
                                "arrangement": "Top",
                                "alignment": "Start"
                            },
                            {
                                "type": "CVText",
                                "name": "Text",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 0,
                                    "paddingEnd": 0,
                                    "paddingBottom": 0
                                },
                                "text": "[Degree Name, e.g., Bachelor of Science in Computer Science]",
                                "fontSize": 18,
                                "textColor": "#FF000000",
                                "textAlign": "Unspecified",
                                "fontFamily": "Serif",
                                "bold": true
                            },
                            {
                                "type": "CVLayout",
                                "name": "Layout",
                                "layoutProperties": {
                                    "width": 1,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 16,
                                    "paddingEnd": 0,
                                    "paddingBottom": 16
                                },
                                "children": [
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": 1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "[University Name], [City, State]",
                                        "fontSize": 16,
                                        "textColor": "#FF000000",
                                        "textAlign": "Start",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    },
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": 1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": " [Start Date] - [End Date]",
                                        "fontSize": 16,
                                        "textColor": "#FF000000",
                                        "textAlign": "End",
                                        "fontFamily": "Serif",
                                        "bold": false
                                    }
                                ],
                                "orientation": "Horizontal",
                                "arrangement": "Top",
                                "alignment": "Start"
                            },
                            {
                                "type": "CVText",
                                "name": "Text",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 0,
                                    "paddingEnd": 0,
                                    "paddingBottom": 0
                                },
                                "text": "GPA: [Your GPA, if applicable]",
                                "fontSize": 16,
                                "textColor": "#FF000000",
                                "textAlign": "Unspecified",
                                "fontFamily": "Serif",
                                "bold": false
                            },
                            {
                                "type": "CVLayout",
                                "name": "Layout",
                                "layoutProperties": {
                                    "width": 1,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 0,
                                    "paddingTop": 24,
                                    "paddingEnd": 0,
                                    "paddingBottom": 8
                                },
                                "children": [
                                    {
                                        "type": "CVText",
                                        "name": "Text",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 0,
                                            "paddingEnd": 0,
                                            "paddingBottom": 0
                                        },
                                        "text": "Skills",
                                        "fontSize": 22,
                                        "textColor": "#FF000000",
                                        "textAlign": "Unspecified",
                                        "fontFamily": "Serif",
                                        "bold": true
                                    },
                                    {
                                        "type": "CVDivider",
                                        "name": "Divider",
                                        "layoutProperties": {
                                            "width": 0,
                                            "height": 0,
                                            "weight": -1.0,
                                            "background": "#00000000",
                                            "paddingStart": 0,
                                            "paddingTop": 8,
                                            "paddingEnd": 0,
                                            "paddingBottom": 8
                                        },
                                        "orientation": "Horizontal",
                                        "thickness": 1.0,
                                        "color": "#FF000000"
                                    }
                                ],
                                "orientation": "Vertical",
                                "arrangement": "Top",
                                "alignment": "Start"
                            },
                            {
                                "type": "CVList",
                                "name": "List",
                                "layoutProperties": {
                                    "width": 0,
                                    "height": 0,
                                    "weight": -1.0,
                                    "background": "#00000000",
                                    "paddingStart": 16,
                                    "paddingTop": 0,
                                    "paddingEnd": 0,
                                    "paddingBottom": 0
                                },
                                "items": [
                                    "**Programming Languages:** Python, Java, JavaScript, C++",
                                    "**Frameworks:** React, Node.js, Django, Spring Boot",
                                    "**Databases:** SQL, MongoDB, PostgreSQL",
                                    "**Tools & Technologies:** Git, Docker, AWS, JIRA, Agile Methodologies",
                                    "**Soft Skills:** Problem-Solving, Teamwork, Communication, Time Management"
                                ],
                                "fontSize": 16,
                                "textColor": "#FF000000",
                                "textAlign": "Unspecified",
                                "fontFamily": "None",
                                "style": "Bullet",
                                "bold": false
                            }
                        ],
                        "orientation": "Vertical",
                        "arrangement": "Top",
                        "alignment": "Start"
                    }
                ]
            }
        ],
        "imageData": null
    }
""".trimIndent()
val demoCVJson = template.toJsonString()