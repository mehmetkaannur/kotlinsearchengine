package websearch

import org.jsoup.nodes.Document

class URL(private val url: String) {
  override fun toString(): String {
    return this.url
  }
}

class WebPage(private val document: Document) {
  fun extractWords(): List<String> = document.text().lowercase().split(' ')
    .map { chr -> chr.filter { it !in ".," } }
}