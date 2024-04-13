package me.lab.happyprimo.data.models


import com.google.gson.annotations.SerializedName

data class FeedResponseModel(
    @SerializedName("item")
    val item: List<Item?>?,
    @SerializedName("rss")
    val rss: Rss?
) {
    data class Item(
        @SerializedName("title")
        val title: Title?
    ) {
        data class Title(
            @SerializedName("link")
            val link: Link?,
            @SerializedName("title_data")
            val titleData: String?
        ) {
            data class Link(
                @SerializedName("guid")
                val guid: Guid?,
                @SerializedName("link_data")
                val linkData: String?
            ) {
                data class Guid(
                    @SerializedName("category")
                    val category: List<String?>?,
                    @SerializedName("dc:creator")
                    val dcCreator: DcCreator?,
                    @SerializedName("guid_data")
                    val guidData: String?
                ) {
                    data class DcCreator(
                        @SerializedName("dc:creator_data")
                        val dcCreatorData: String?,
                        @SerializedName("pubDate")
                        val pubDate: PubDate?
                    ) {
                        data class PubDate(
                            @SerializedName("atom:updated")
                            val atomUpdated: AtomUpdated?,
                            @SerializedName("pubDate_data")
                            val pubDateData: String?
                        ) {
                            data class AtomUpdated(
                                @SerializedName("atom:updated_data")
                                val atomUpdatedData: String?,
                                @SerializedName("content:encoded")
                                val contentEncoded: ContentEncoded?
                            ) {
                                data class ContentEncoded(
                                    @SerializedName("content:encoded_data")
                                    val contentEncodedData: String?
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    data class Rss(
        @SerializedName("channel")
        val channel: Channel?
    ) {
        data class Channel(
            @SerializedName("title")
            val title: Title?
        ) {
            data class Title(
                @SerializedName("description")
                val description: Description?,
                @SerializedName("title_data")
                val titleData: String?
            ) {
                data class Description(
                    @SerializedName("description_data")
                    val descriptionData: String?,
                    @SerializedName("link")
                    val link: Link?
                ) {
                    data class Link(
                        @SerializedName("image")
                        val image: Image?,
                        @SerializedName("link_data")
                        val linkData: String?
                    ) {
                        data class Image(
                            @SerializedName("url")
                            val url: Url?
                        ) {
                            data class Url(
                                @SerializedName("title")
                                val title: Title?,
                                @SerializedName("url_data")
                                val urlData: String?
                            ) {
                                data class Title(
                                    @SerializedName("link")
                                    val link: Link?,
                                    @SerializedName("title_data")
                                    val titleData: String?
                                ) {
                                    data class Link(
                                        @SerializedName("generator")
                                        val generator: Generator?,
                                        @SerializedName("link_data")
                                        val linkData: String?
                                    ) {
                                        data class Generator(
                                            @SerializedName("generator_data")
                                            val generatorData: String?,
                                            @SerializedName("lastBuildDate")
                                            val lastBuildDate: LastBuildDate?
                                        ) {
                                            data class LastBuildDate(
                                                @SerializedName("atom:link")
                                                val atomLink: AtomLink?,
                                                @SerializedName("lastBuildDate_data")
                                                val lastBuildDateData: String?
                                            ) {
                                                data class AtomLink(
                                                    @SerializedName("webMaster")
                                                    val webMaster: WebMaster?
                                                ) {
                                                    data class WebMaster(
                                                        @SerializedName("atom:link")
                                                        val atomLink: AtomLink?,
                                                        @SerializedName("webMaster_data")
                                                        val webMasterData: String?
                                                    ) {
                                                        class AtomLink
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}