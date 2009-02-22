package org.jetbrains.plugins.clojure.editor.selection;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.plugins.clojure.psi.ClojurePsiElement;
import org.jetbrains.plugins.clojure.psi.api.ClList;
import org.jetbrains.plugins.clojure.psi.api.ClMap;
import org.jetbrains.plugins.clojure.psi.api.ClSet;
import org.jetbrains.plugins.clojure.psi.api.ClVector;

import java.util.List;

/**
 * @author ilyas
 */
public class ClojureListSelectioner extends ClojureBasicSelectioner {
  public boolean canSelect(PsiElement e) {
    return e instanceof ClList ||
        e instanceof ClVector ||
        e instanceof ClSet ||
        e instanceof ClMap;
  }

  @Override
   public List<TextRange> select(PsiElement element, CharSequence editorText, int cursorOffset, Editor editor) {
    List<TextRange> result = super.select(element, editorText, cursorOffset, editor);
    if (element instanceof ClojurePsiElement) {
      final ClojurePsiElement psi = (ClojurePsiElement) element;
      final PsiElement fst = psi.getFirstNonLeafElement();
      final PsiElement lst = psi.getLastNonLeafElement();
      final int start = fst != null ? fst.getTextRange().getStartOffset() : psi.getTextRange().getStartOffset();
      final int end = lst != null ? lst.getTextRange().getEndOffset() : psi.getTextRange().getEndOffset();
      result.add(new TextRange(start, end));
    }

    return result;
  }
}
