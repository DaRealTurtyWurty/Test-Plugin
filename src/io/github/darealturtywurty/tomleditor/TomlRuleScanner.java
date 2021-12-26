package io.github.darealturtywurty.tomleditor;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class TomlRuleScanner extends RuleBasedScanner {

	private static class RegexRule implements IPredicateRule {

		private final String regex;
		private final IToken token;
		private final StringBuilder str = new StringBuilder();

		public RegexRule(final String regex, final IToken token) {
			this.regex = regex;
			this.token = token;
		}

		protected IToken doEvaluate(final ICharacterScanner scanner, final boolean resume) {
			if (this.str.isEmpty()) {
				final char[][] sequence = scanner.getLegalLineDelimiters();
				for (final char[] subsequence : sequence) {
					this.str.append(String.valueOf(subsequence));
				}
				System.out.println(this.str.toString());
			}
			return this.token;
		}

		@Override
		public IToken evaluate(final ICharacterScanner scanner) {
			return evaluate(scanner, false);
		}

		@Override
		public IToken evaluate(final ICharacterScanner scanner, final boolean resume) {
			return doEvaluate(scanner, resume);
		}

		@Override
		public IToken getSuccessToken() {
			return this.token;
		}
	}

	static final Color DEFAULT_COLOR = new Color(Display.getCurrent(), new RGB(255, 0, 0));
	static final Color COMMENT_COLOR = new Color(Display.getCurrent(), new RGB(0, 200, 0));

	public TomlRuleScanner() {
		final IToken defaultToken = new Token(new TextAttribute(DEFAULT_COLOR));
		final IToken commentToken = new Token(new TextAttribute(COMMENT_COLOR));
		final var rules = new IRule[2];
		// rules[0] = new SingleLineRule("[", "]", defaultToken);
		rules[0] = new RegexRule(
				"^\s*(\\[\\[\\]\\]|\\[\\[\\..*\\]\\]|\\[\\[.*\\.\\]\\]|\\[\\[.*\\.\\..*\\]\\]|\\[\\[.*[\\[\\]#].*\\]\\]|\\[\\[.*\\]\\].+\n)",
				commentToken);
		setRules(rules);
	}
}
