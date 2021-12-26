package io.github.darealturtywurty.tomleditor;

import org.eclipse.ui.editors.text.TextEditor;

public class TomlEditor extends TextEditor {

	public TomlEditor() {
		setSourceViewerConfiguration(new TomlSourceViewerConfig());
	}
}
