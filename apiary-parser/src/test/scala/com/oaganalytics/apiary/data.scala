package com.oaganalytics.apib

object Data {
  val shortMember = """
{
  "element": "member",
  "content": {
    "key": {
      "element": "string",
      "content": "Content-Type"
    },
    "value": {
      "element": "string",
      "content": "application/json"
    }
  }
}
"""

  val longMember = """
{
  "element": "member",
  "meta": {
    "description": "Random description"
  },
  "attributes": {
    "typeAttributes": [
      "required"
    ]
  },
  "content": {
    "key": {
      "element": "string",
      "content": "versions"
    },
    "value": {
      "element": "array",
      "attributes": {
        "default": [
          {
            "element": "string",
            "content": "[]"
          }
        ]
      },
      "content": [
        {
          "element": "string",
          "content": "[unique-id]"
        }
      ]
    }
  }
}
"""

  val headers = """
{
  "element": "httpHeaders",
  "content": [
    {
      "element": "member",
      "content": {
        "key": {
          "element": "string",
          "content": "Content-Type"
        },
        "value": {
          "element": "string",
          "content": "application/json"
        }
      }
    }
  ]
}
"""
  val asset = """
{
  "element": "asset",
  "meta": {
    "classes": [
      "messageBody"
    ]
  },
  "attributes": {
    "contentType": "application/json"
  },
  "content": "some-stuff"
}
"""

  val response = """
{
  "element": "httpResponse",
  "attributes": {
    "statusCode": "200",
    "headers": {
      "element": "httpHeaders",
      "content": [
        {
          "element": "member",
          "content": {
            "key": {
              "element": "string",
              "content": "Content-Type"
            },
            "value": {
              "element": "string",
              "content": "application/json"
            }
          }
        }
      ]
    }
  },
  "content": [
    {
      "element": "asset",
      "meta": {
        "classes": [
          "messageBody"
        ]
      },
      "attributes": {
        "contentType": "application/json"
      },
      "content": "some-stuff"
    }
  ]
}
"""

  val request = """
{
  "element": "httpRequest",
  "attributes": {
    "method": "PUT",
    "title": "",
    "headers": {
      "element": "httpHeaders",
      "content": [
        {
          "element": "member",
          "content": {
            "key": {
              "element": "string",
              "content": "Content-Type"
            },
            "value": {
              "element": "string",
              "content": "application/json"
            }
          }
        }
      ]
    }
  },
  "content": [
    {
      "element": "asset",
      "meta": {
        "classes": [
          "messageBody"
        ]
      },
      "attributes": {
        "contentType": "application/json"
      },
      "content": "some-stuff"
    }
  ]
}
"""

  val shortRequest = """
{
  "element": "httpRequest",
  "attributes": {
    "method": "GET"
  },
  "content": []
}
"""

  val transaction = """
{
  "element": "httpTransaction",
  "content": [
    {
      "element": "httpRequest",
      "attributes": {
        "method": "GET"
      },
      "content": []
    },
    {
      "element": "httpResponse",
      "attributes": {
        "statusCode": "200",
        "headers": {
          "element": "httpHeaders",
          "content": [
            {
              "element": "member",
              "content": {
                "key": {
                  "element": "string",
                  "content": "Content-Type"
                },
                "value": {
                  "element": "string",
                  "content": "application/json"
                }
              }
            }
          ]
        }
      },
      "content": [
        {
          "element": "asset",
          "meta": {
            "classes": [
              "messageBody"
            ]
          },
          "attributes": {
            "contentType": "application/json"
          },
          "content": "some-stuff"
        }
      ]
    }
  ]
}
"""

  val meta = """
{
  "description": "Unique ID"
}
"""

  val structObject = """
{
  "element": "object",
  "content": [
    {
      "element": "member",
      "meta": {
        "description": "Unique ID"
      },
      "attributes": {
        "typeAttributes": [
          "optional"
        ]
      },
      "content": {
        "key": {
          "element": "string",
          "content": "activeNotificationEmail"
        },
        "value": {
          "element": "string",
          "content": "foo@bar.com"
        }
      }
    }
  ]
}
"""

  val memberRef = """
{
  "element": "member",
  "attributes": {
    "typeAttributes": [
      "required"
    ]
  },
  "content": {
    "key": {
      "element": "string",
      "content": "configSelection"
    },
    "value": {
      "element": "Config Selection"
    }
  }
}
"""

  val dataStructure = """
{
  "element": "dataStructure",
  "content": [
    {
      "element": "object",
      "content": [
        {
          "element": "member",
          "attributes": {
            "typeAttributes": [
              "required"
            ]
          },
          "content": {
            "key": {
              "element": "string",
              "content": "configSelection"
            },
            "value": {
              "element": "object",
              "content": [
                {
                  "element": "member",
                  "meta": {
                    "description": "Unique ID"
                  },
                  "attributes": {
                    "typeAttributes": [
                      "optional"
                    ]
                  },
                  "content": {
                    "key": {
                      "element": "string",
                      "content": "activeNotificationEmail"
                    },
                    "value": {
                      "element": "string",
                      "content": "foo@bar.com"
                    }
                  }
                },
                {
                  "element": "member",
                  "meta": {
                    "description": "User email"
                  },
                  "attributes": {
                    "typeAttributes": [
                      "required"
                    ]
                  },
                  "content": {
                    "key": {
                      "element": "string",
                      "content": "'email'"
                    },
                    "value": {
                      "element": "string",
                      "content": "foo@bar.com"
                    }
                  }
                }
              ]
            }
          }
        }
      ]
    }
  ]
}
"""

  val anotherMember = """
{
  "element": "member",
  "content": {
    "key": {
      "element": "string",
      "content": "plays"
    },
    "value": {
      "element": "array",
      "content": [
        {
          "element": "Play"
        }
      ]
    }
  }
}
"""

  val mixedResponse = """
{
  "element": "httpResponse",
  "attributes": {
    "statusCode": "200",
    "headers": {
      "element": "httpHeaders",
      "content": [
        {
          "element": "member",
          "content": {
            "key": {
              "element": "string",
              "content": "Content-Type"
            },
            "value": {
              "element": "string",
              "content": "application/json"
            }
          }
        }
      ]
    }
  },
  "content": [
    {
      "element": "dataStructure",
      "content": [
        {
          "element": "object",
          "content": [
            {
              "element": "member",
              "content": {
                "key": {
                  "element": "string",
                  "content": "plays"
                },
                "value": {
                  "element": "array",
                  "content": [
                    {
                      "element": "Play"
                    }
                  ]
                }
              }
            }
          ]
        }
      ]
    },
    {
      "element": "asset",
      "meta": {
        "classes": [
          "messageBody"
        ]
      },
      "attributes": {
        "contentType": "application/json"
      },
      "content": "some content"
    },
    {
      "element": "asset",
      "meta": {
        "classes": [
          "messageBodySchema"
        ]
      },
      "attributes": {
        "contentType": "application/json"
      },
      "content": "some content"
    }
  ]
}
"""

  val enum = """
{
  "key": {
    "element": "string",
    "content": "percentInTraining"
  },
  "value": {
    "element": "enum",
    "attributes": {
      "samples": [
        [
          {
            "element": "string",
            "content": "70%"
          }
        ]
      ],
      "default": [
        {
          "element": "string",
          "content": "70%"
        }
      ]
    },
    "content": [
      {
        "element": "string",
        "content": "70%"
      },
      {
        "element": "string",
        "content": "80%"
      },
      {
        "element": "string",
        "content": "90%"
      }
    ]
  }
}
"""


  val category = """
{
  "element": "category",
  "meta": {
    "classes": [
      "resourceGroup"
    ],
    "title": "Regions"
  },
  "content": [
    {
      "element": "resource",
      "meta": {
        "title": "Assets"
      },
      "attributes": {
        "href": "/regions"
      },
      "content": [
        {
          "element": "copy",
          "content": "SomeOtherStuff"
        }
      ]
    }
  ]
}
"""


  val resource = """
{
  "element": "resource",
  "meta": {
    "title": "Play"
  },
  "attributes": {
    "href": "/plays"
  },
  "content": [
    {
      "element": "dataStructure",
      "content": [
        {
          "element": "object",
          "meta": {
            "id": "Play"
          },
          "content": [
            {
              "element": "member",
              "meta": {
                "description": "Unique identifier"
              },
              "attributes": {
                "typeAttributes": [
                  "required"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "id"
                },
                "value": {
                  "element": "string",
                  "content": "unique-id"
                }
              }
            },
            {
              "element": "member",
              "meta": {
                "description": "Description"
              },
              "attributes": {
                "typeAttributes": [
                  "required"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "versions"
                },
                "value": {
                  "element": "array",
                  "attributes": {
                    "default": [
                      {
                        "element": "string",
                        "content": "[]"
                      }
                    ]
                  },
                  "content": [
                    {
                      "element": "string",
                      "content": "[unique-id]"
                    }
                  ]
                }
              }
            },
            {
              "element": "member",
              "meta": {
                "description": "User friendly identifier"
              },
              "attributes": {
                "typeAttributes": [
                  "required"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "name"
                },
                "value": {
                  "element": "string",
                  "content": "foobar"
                }
              }
            }
          ]
        }
      ]
    },
    {
      "element": "transition",
      "meta": {
        "title": "Request Plays"
      },
      "content": [
        {
          "element": "httpTransaction",
          "content": [
            {
              "element": "copy",
              "content": "Random comment"
            },
            {
              "element": "httpRequest",
              "attributes": {
                "method": "GET"
              },
              "content": []
            },
            {
              "element": "httpResponse",
              "attributes": {
                "statusCode": "200",
                "headers": {
                  "element": "httpHeaders",
                  "content": [
                    {
                      "element": "member",
                      "content": {
                        "key": {
                          "element": "string",
                          "content": "Content-Type"
                        },
                        "value": {
                          "element": "string",
                          "content": "application/json"
                        }
                      }
                    }
                  ]
                }
              },
              "content": [
                {
                  "element": "dataStructure",
                  "content": [
                    {
                      "element": "object",
                      "content": [
                        {
                          "element": "member",
                          "content": {
                            "key": {
                              "element": "string",
                              "content": "plays"
                            },
                            "value": {
                              "element": "array",
                              "content": [
                                {
                                  "element": "Play"
                                }
                              ]
                            }
                          }
                        }
                      ]
                    }
                  ]
                },
                {
                  "element": "asset",
                  "meta": {
                    "classes": [
                      "messageBody"
                    ]
                  },
                  "attributes": {
                    "contentType": "application/json"
                  },
                  "content": "some content"
                },
                {
                  "element": "asset",
                  "meta": {
                    "classes": [
                      "messageBodySchema"
                    ]
                  },
                  "attributes": {
                    "contentType": "application/json"
                  },
                  "content": "some content"
                }
              ]
            }
          ]
        }
      ]
    }
  ]
}
"""

  val anotherResource = """
{
  "element": "resource",
  "meta": {
    "title": "Pictures"
  },
  "attributes": {
    "href": "/Pictures"
  },
  "content": [
    {
      "element": "copy",
      "content": "Pictures"
    },
    {
      "element": "transition",
      "meta": {
        "title": "Request a Picture"
      },
      "attributes": {
        "href": "/pictures{?version}{?pic}{?site}{?day}{?features}{?used}",
        "hrefVariables": {
          "element": "hrefVariables",
          "content": [
            {
              "element": "member",
              "meta": {
                "description": "Analysis Version ID"
              },
              "attributes": {
                "typeAttributes": [
                  "required"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "version"
                },
                "value": {
                  "element": "string",
                  "content": "unique-id"
                }
              }
            },
            {
              "element": "member",
              "meta": {
                "description": "Pic ID"
              },
              "attributes": {
                "typeAttributes": [
                  "required"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "pic"
                },
                "value": {
                  "element": "string",
                  "content": "unique-id"
                }
              }
            },
            {
              "element": "member",
              "meta": {
                "description": "Site ID"
              },
              "attributes": {
                "typeAttributes": [
                  "required"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "site"
                },
                "value": {
                  "element": "string",
                  "content": "unique-id"
                }
              }
            },
            {
              "element": "member",
              "meta": {
                "description": "Day ID"
              },
              "attributes": {
                "typeAttributes": [
                  "required"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "day"
                },
                "value": {
                  "element": "number",
                  "content": 0
                }
              }
            },
            {
              "element": "member",
              "meta": {
                "description": "TODO Description required"
              },
              "attributes": {
                "typeAttributes": [
                  "optional"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "features"
                },
                "value": {
                  "element": "number",
                  "attributes": {
                    "default": "100"
                  },
                  "content": 4
                }
              }
            },
            {
              "element": "member",
              "meta": {
                "description": "TODO needs a description"
              },
              "attributes": {
                "typeAttributes": [
                  "required"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "used"
                },
                "value": {
                  "element": "boolean",
                  "content": false
                }
              }
            }
          ]
        },
        "data": {
          "element": "dataStructure",
          "content": [
            {
              "element": "object",
              "content": [
                {
                  "element": "member",
                  "attributes": {
                    "typeAttributes": [
                      "required"
                    ]
                  },
                  "content": {
                    "key": {
                      "element": "string",
                      "content": "picture"
                    },
                    "value": {
                      "element": "object",
                      "content": [
                        {
                          "element": "member",
                          "meta": {
                            "description": "Unique Identifier"
                          },
                          "attributes": {
                            "typeAttributes": [
                              "required"
                            ]
                          },
                          "content": {
                            "key": {
                              "element": "string",
                              "content": "id"
                            },
                            "value": {
                              "element": "string",
                              "content": "unique-id"
                            }
                          }
                        },
                        {
                          "element": "member",
                          "meta": {
                            "description": "Type of picture"
                          },
                          "attributes": {
                            "typeAttributes": [
                              "required"
                            ]
                          },
                          "content": {
                            "key": {
                              "element": "string",
                              "content": "type"
                            },
                            "value": {
                              "element": "string",
                              "content": "foobar"
                            }
                          }
                        },
                        {
                          "element": "member",
                          "meta": {
                            "description": "Configuration"
                          },
                          "attributes": {
                            "typeAttributes": [
                              "required"
                            ]
                          },
                          "content": {
                            "key": {
                              "element": "string",
                              "content": "options"
                            },
                            "value": {
                              "element": "object",
                              "content": [
                                {
                                  "element": "string",
                                  "content": "{}"
                                }
                              ]
                            }
                          }
                        },
                        {
                          "element": "member",
                          "attributes": {
                            "typeAttributes": [
                              "required"
                            ]
                          },
                          "content": {
                            "key": {
                              "element": "string",
                              "content": "series"
                            },
                            "value": {
                              "element": "array",
                              "attributes": {
                                "samples": [
                                  [
                                    {
                                      "element": "[]"
                                    }
                                  ]
                                ]
                              },
                              "content": [
                                {
                                  "element": "array",
                                  "attributes": {
                                    "samples": [
                                      [
                                        {
                                          "element": "children"
                                        }
                                      ]
                                    ]
                                  },
                                  "content": [
                                    {
                                      "element": "array",
                                      "content": [
                                        {
                                          "element": "children"
                                        }
                                      ]
                                    },
                                    {
                                      "element": "object",
                                      "attributes": {
                                        "samples": [
                                          [
                                            {
                                              "element": "string",
                                              "content": "Attributes"
                                            }
                                          ]
                                        ]
                                      },
                                      "content": [
                                        {
                                          "element": "member",
                                          "attributes": {
                                            "typeAttributes": [
                                              "required"
                                            ]
                                          },
                                          "content": {
                                            "key": {
                                              "element": "string",
                                              "content": "name"
                                            },
                                            "value": {
                                              "element": "string",
                                              "content": "ppi"
                                            }
                                          }
                                        },
                                        {
                                          "element": "member",
                                          "attributes": {
                                            "typeAttributes": [
                                              "required"
                                            ]
                                          },
                                          "content": {
                                            "key": {
                                              "element": "string",
                                              "content": "type"
                                            },
                                            "value": {
                                              "element": "string",
                                              "content": "number"
                                            }
                                          }
                                        },
                                        {
                                          "element": "member",
                                          "attributes": {
                                            "typeAttributes": [
                                              "required"
                                            ]
                                          },
                                          "content": {
                                            "key": {
                                              "element": "string",
                                              "content": "categoryName"
                                            },
                                            "value": {
                                              "element": "string",
                                              "content": "header"
                                            }
                                          }
                                        },
                                        {
                                          "element": "member",
                                          "attributes": {
                                            "typeAttributes": [
                                              "required"
                                            ]
                                          },
                                          "content": {
                                            "key": {
                                              "element": "string",
                                              "content": "diameter"
                                            },
                                            "value": {
                                              "element": "number",
                                              "content": 12.126
                                            }
                                          }
                                        },
                                        {
                                          "element": "member",
                                          "attributes": {
                                            "typeAttributes": [
                                              "required"
                                            ]
                                          },
                                          "content": {
                                            "key": {
                                              "element": "string",
                                              "content": "favourite"
                                            },
                                            "value": {
                                              "element": "boolean",
                                              "content": true
                                            }
                                          }
                                        }
                                      ]
                                    }
                                  ]
                                }
                              ]
                            }
                          }
                        }
                      ]
                    }
                  }
                }
              ]
            }
          ]
        }
      },
      "content": [
        {
          "element": "httpTransaction",
          "content": [
            {
              "element": "httpRequest",
              "attributes": {
                "method": "GET"
              },
              "content": []
            },
            {
              "element": "httpResponse",
              "attributes": {
                "statusCode": "200",
                "headers": {
                  "element": "httpHeaders",
                  "content": [
                    {
                      "element": "member",
                      "content": {
                        "key": {
                          "element": "string",
                          "content": "Content-Type"
                        },
                        "value": {
                          "element": "string",
                          "content": "application/json"
                        }
                      }
                    }
                  ]
                }
              },
              "content": []
            }
          ]
        }
      ]
    }
  ]
}
"""

  val complex = """
{
  "element": "resource",
  "meta": {
    "title": "Filter Config"
  },
  "attributes": {
    "href": "/filter-configs"
  },
  "content": [
    {
      "element": "dataStructure",
      "content": [
        {
          "element": "object",
          "meta": {
            "id": "Filter Config"
          },
          "content": [
            {
              "element": "member",
              "meta": {
                "description": "type of Filter Config"
              },
              "attributes": {
                "typeAttributes": [
                  "required"
                ]
              },
              "content": {
                "key": {
                  "element": "string",
                  "content": "type"
                },
                "value": {
                  "element": "enum",
                  "attributes": {
                    "samples": [
                      [
                        {
                          "element": "string",
                          "content": "string"
                        }
                      ]
                    ]
                  },
                  "content": [
                    {
                      "element": "string",
                      "content": "string"
                    },
                    {
                      "element": "string",
                      "content": "number"
                    },
                    {
                      "element": "string",
                      "content": "date"
                    }
                  ]
                }
              }
            }
          ]
        }
      ]
    }
  ]
}
"""

  val literalObject = """
{
  "element": "object",
  "content": [
    {
      "element": "string",
      "content": "{}"
    }
  ]
}
"""

  val queryParameters = """
{
  "element": "transition",
  "meta": {
    "title": "Request Valid Version Name"
  },
  "attributes": {
    "href": "/versions/validate{?name}",
    "hrefVariables": {
      "element": "hrefVariables",
      "content": [
        {
          "element": "member",
          "meta": {
            "description": "Requested name for validation"
          },
          "attributes": {
            "typeAttributes": [
              "required"
            ]
          },
          "content": {
            "key": {
              "element": "string",
              "content": "name"
            },
            "value": {
              "element": "string",
              "content": "foobar"
            }
          }
        }
      ]
    }
  },
  "content": [
    {
      "element": "copy",
      "content": "Random Comment"
    },
    {
      "element": "httpTransaction",
      "content": [
        {
          "element": "httpRequest",
          "attributes": {
            "method": "GET"
          },
          "content": []
        },
        {
          "element": "httpResponse",
          "attributes": {
            "statusCode": "200",
            "headers": {
              "element": "httpHeaders",
              "content": [
                {
                  "element": "member",
                  "content": {
                    "key": {
                      "element": "string",
                      "content": "Content-Type"
                    },
                    "value": {
                      "element": "string",
                      "content": "application/json"
                    }
                  }
                }
              ]
            }
          },
          "content": [
            {
              "element": "dataStructure",
              "content": [
                {
                  "element": "object",
                  "content": [
                    {
                      "element": "member",
                      "meta": {
                        "description": "Is valid name"
                      },
                      "attributes": {
                        "typeAttributes": [
                          "required"
                        ]
                      },
                      "content": {
                        "key": {
                          "element": "string",
                          "content": "valid"
                        },
                        "value": {
                          "element": "boolean"
                        }
                      }
                    },
                    {
                      "element": "member",
                      "meta": {
                        "description": "Requested name"
                      },
                      "attributes": {
                        "typeAttributes": [
                          "required"
                        ]
                      },
                      "content": {
                        "key": {
                          "element": "string",
                          "content": "name"
                        },
                        "value": {
                          "element": "string"
                        }
                      }
                    }
                  ]
                }
              ]
            },
            {
              "element": "asset",
              "meta": {
                "classes": [
                  "messageBody"
                ]
              },
              "attributes": {
                "contentType": "application/json"
              },
              "content": "{\n  \"valid\": false,\n  \"name\": \"\"\n}"
            },
            {
              "element": "asset",
              "meta": {
                "classes": [
                  "messageBodySchema"
                ]
              },
              "attributes": {
                "contentType": "application/json"
              },
              "content": "{\n  \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n  \"type\": \"object\",\n  \"properties\": {\n    \"valid\": {\n      \"type\": \"boolean\",\n      \"description\": \"Is valid name\"\n    },\n    \"name\": {\n      \"type\": \"string\",\n      \"description\": \"Requested name\"\n    }\n  },\n  \"required\": [\n    \"valid\",\n    \"name\"\n  ]\n}"
            }
          ]
        }
      ]
    }
  ]
}
"""

  val objectDS = """
{
  "element": "dataStructure",
  "content": [
    {
      "element": "object"
    }
  ]
}
"""

  val objectElement = """
{
  "element": "object"
}
"""

  val arrayObjectElement = """
{
  "element": "[]"
}
"""

  val document = """
{
  "element": "parseResult",
  "content": [
    {
      "element": "category",
      "meta": {
        "classes": [
          "api"
        ],
        "title": "API"
      },
      "attributes": {
        "meta": [
          {
            "element": "member",
            "meta": {
              "classes": [
                "user"
              ]
            },
            "content": {
              "key": {
                "element": "string",
                "content": "FORMAT"
              },
              "value": {
                "element": "string",
                "content": "1A"
              }
            }
          },
          {
            "element": "member",
            "meta": {
              "classes": [
                "user"
              ]
            },
            "content": {
              "key": {
                "element": "string",
                "content": "HOST"
              },
              "value": {
                "element": "string",
                "content": "https://oaganalitics.com/api"
              }
            }
          }
        ]
      },
      "content": [
        {
          "element": "copy",
          "content": "API based on the REST/Rails API standard"
        },
        {
          "element": "category",
          "meta": {
            "classes": [
              "resourceGroup"
            ],
            "title": ""
          },
          "content": [
            {
              "element": "resource",
              "meta": {
                "title": "API Root"
              },
              "attributes": {
                "href": "/"
              },
              "content": [
                {
                  "element": "copy",
                  "content": ""
                },
                {
                  "element": "transition",
                  "meta": {
                    "title": "Retrieve the Entry Point"
                  },
                  "content": [
                    {
                      "element": "httpTransaction",
                      "content": [
                        {
                          "element": "httpRequest",
                          "attributes": {
                            "method": "GET"
                          },
                          "content": []
                        },
                        {
                          "element": "httpResponse",
                          "attributes": {
                            "statusCode": "200",
                            "headers": {
                              "element": "httpHeaders",
                              "content": [
                                {
                                  "element": "member",
                                  "content": {
                                    "key": {
                                      "element": "string",
                                      "content": "Content-Type"
                                    },
                                    "value": {
                                      "element": "string",
                                      "content": "application/json"
                                    }
                                  }
                                }
                              ]
                            }
                          },
                          "content": [
                            {
                              "element": "dataStructure",
                              "content": [
                                {
                                  "element": "object"
                                }
                              ]
                            },
                            {
                              "element": "asset",
                              "meta": {
                                "classes": [
                                  "messageBody"
                                ]
                              },
                              "attributes": {
                                "contentType": "application/json"
                              },
                              "content": "{}"
                            },
                            {
                              "element": "asset",
                              "meta": {
                                "classes": [
                                  "messageBodySchema"
                                ]
                              },
                              "attributes": {
                                "contentType": "application/json"
                              },
                              "content": "{\n  \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n  \"type\": \"object\",\n  \"properties\": {}\n}"
                            }
                          ]
                        }
                      ]
                    }
                  ]
                }
              ]
            },
            {
              "element": "resource",
              "meta": {
                "title": "Company Company"
              },
              "attributes": {
                "href": "/companies"
              },
              "content": [
                {
                  "element": "dataStructure",
                  "content": [
                    {
                      "element": "object",
                      "meta": {
                        "id": "Company Company"
                      },
                      "content": [
                        {
                          "element": "member",
                          "meta": {
                            "description": "Unique identifier"
                          },
                          "attributes": {
                            "typeAttributes": [
                              "required"
                            ]
                          },
                          "content": {
                            "key": {
                              "element": "string",
                              "content": "id"
                            },
                            "value": {
                              "element": "string",
                              "content": "unique-id"
                            }
                          }
                        },
                        {
                          "element": "member",
                          "meta": {
                            "description": "Array of User ID's that belong to it"
                          },
                          "attributes": {
                            "typeAttributes": [
                              "required"
                            ]
                          },
                          "content": {
                            "key": {
                              "element": "string",
                              "content": "users"
                            },
                            "value": {
                              "element": "array",
                              "content": [
                                {
                                  "element": "string",
                                  "content": "[unique-id]"
                                }
                              ]
                            }
                          }
                        },
                        {
                          "element": "member",
                          "meta": {
                            "description": "Array of Version ID's that belong to it"
                          },
                          "attributes": {
                            "typeAttributes": [
                              "required"
                            ]
                          },
                          "content": {
                            "key": {
                              "element": "string",
                              "content": "versions"
                            },
                            "value": {
                              "element": "array",
                              "attributes": {
                                "default": [
                                  {
                                    "element": "string",
                                    "content": "[]"
                                  }
                                ]
                              },
                              "content": [
                                {
                                  "element": "string",
                                  "content": "[unique-id]"
                                }
                              ]
                            }
                          }
                        }
                      ]
                    }
                  ]
                },
                {
                  "element": "transition",
                  "meta": {
                    "title": "Request an Account"
                  },
                  "attributes": {
                    "href": "/companies/{company}",
                    "hrefVariables": {
                      "element": "hrefVariables",
                      "content": [
                        {
                          "element": "member",
                          "meta": {
                            "description": "Company ID"
                          },
                          "attributes": {
                            "typeAttributes": [
                              "required"
                            ]
                          },
                          "content": {
                            "key": {
                              "element": "string",
                              "content": "company"
                            },
                            "value": {
                              "element": "string",
                              "content": ""
                            }
                          }
                        }
                      ]
                    }
                  },
                  "content": [
                    {
                      "element": "httpTransaction",
                      "content": [
                        {
                          "element": "httpRequest",
                          "attributes": {
                            "method": "GET"
                          },
                          "content": []
                        },
                        {
                          "element": "httpResponse",
                          "attributes": {
                            "statusCode": "200",
                            "headers": {
                              "element": "httpHeaders",
                              "content": [
                                {
                                  "element": "member",
                                  "content": {
                                    "key": {
                                      "element": "string",
                                      "content": "Content-Type"
                                    },
                                    "value": {
                                      "element": "string",
                                      "content": "application/json"
                                    }
                                  }
                                }
                              ]
                            }
                          },
                          "content": [
                            {
                              "element": "dataStructure",
                              "content": [
                                {
                                  "element": "object",
                                  "content": [
                                    {
                                      "element": "member",
                                      "attributes": {
                                        "typeAttributes": [
                                          "required"
                                        ]
                                      },
                                      "content": {
                                        "key": {
                                          "element": "string",
                                          "content": "company"
                                        },
                                        "value": {
                                          "element": "Company Company"
                                        }
                                      }
                                    }
                                  ]
                                }
                              ]
                            },
                            {
                              "element": "asset",
                              "meta": {
                                "classes": [
                                  "messageBody"
                                ]
                              },
                              "attributes": {
                                "contentType": "application/json"
                              },
                              "content": "{\n    \"company\": {\n        \"id\": 1,\n        \"users\": [2],\n        \"versions\": [7]\n    }\n}\n"
                            },
                            {
                              "element": "asset",
                              "meta": {
                                "classes": [
                                  "messageBodySchema"
                                ]
                              },
                              "attributes": {
                                "contentType": "application/json"
                              },
                              "content": "{\n  \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n  \"type\": \"object\",\n  \"properties\": {\n    \"company\": {\n      \"type\": \"object\",\n      \"properties\": {\n        \"id\": {\n          \"type\": \"string\",\n          \"description\": \"Unique identifier\"\n        },\n        \"users\": {\n          \"type\": \"array\",\n          \"description\": \"Array of User ID's that belong to it\"\n        },\n        \"versions\": {\n          \"type\": \"array\",\n          \"default\": [\n            \"[]\"\n          ],\n          \"description\": \"Array of Version ID's that belong to it\"\n        }\n      },\n      \"required\": [\n        \"id\",\n        \"users\",\n        \"versions\"\n      ]\n    }\n  },\n  \"required\": [\n    \"company\"\n  ]\n}"
                            }
                          ]
                        }
                      ]
                    }
                  ]
                }
              ]
            }
          ]
        }
      ]
    }
  ]
}
"""

}
