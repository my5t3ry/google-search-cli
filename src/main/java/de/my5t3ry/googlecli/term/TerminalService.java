package de.my5t3ry.googlecli.term;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

/** User: my5t3ry Date: 5/5/20 5:07 AM */
public class TerminalService {

  public static Terminal terminal;

  public static void init() throws IOException {
      terminal = TerminalBuilder.builder().system(true).nativeSignals(true).build();
  }
}
