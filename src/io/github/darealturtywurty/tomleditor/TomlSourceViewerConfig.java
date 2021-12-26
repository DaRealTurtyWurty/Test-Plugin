package io.github.darealturtywurty.tomleditor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class TomlSourceViewerConfig extends SourceViewerConfiguration {

	private TomlRuleScanner scanner;

	@Override
	public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer) {
		final var reconciler = new PresentationReconciler();
		final var damageRepairer = new DefaultDamagerRepairer(getTagScanner());
		reconciler.setDamager(damageRepairer, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(damageRepairer, IDocument.DEFAULT_CONTENT_TYPE);
		return reconciler;
	}

	protected TomlRuleScanner getTagScanner() {
		if (this.scanner == null) {
			this.scanner = new TomlRuleScanner();
			this.scanner.setDefaultReturnToken(new Token(new TextAttribute(TomlRuleScanner.DEFAULT_COLOR)));
		}
		return this.scanner;
	}
}
