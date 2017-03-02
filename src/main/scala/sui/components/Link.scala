package sui.components

import outwatch.dom.{VNode, a, href}
import sui.Pages.Page


object Link {
  def apply(page: Page, label: String): VNode = {
    a(href := s"#${page.hash}", label)
  }
}
