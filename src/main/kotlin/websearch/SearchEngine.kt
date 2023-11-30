package websearch

class SearchEngine(private val linkToWebpage: Map<URL, WebPage>) {
  private var index: Map<String, List<SearchResult>> = emptyMap()
  fun compileIndex() {
    val extracted = this.linkToWebpage.flatMap { (a, b) ->
      b.extractWords().map { c -> Pair(c, a) }
    }
    val grouped = extracted.groupBy { it.first }
    index = grouped.mapValues { x -> rank(x.value.map { it.second }) }
  }

  private fun rank(urls: List<URL>): List<SearchResult> = urls.groupBy { it }
    .map { (url, urlList) -> SearchResult(url, urlList.size) }
    .sortedByDescending { it.numRefs }

  fun searchFor(query: String): SearchResultSummary {
    return SearchResultSummary(index.getOrDefault(query, emptyList()), query)
  }

}

class SearchResult(val url: URL, val numRefs: Int)

class SearchResultSummary(val results: List<SearchResult>, val query: String)