package de.my5t3ry.googlecli.history;

import de.my5t3ry.googlecli.command.CommandService;
import org.apache.commons.lang3.StringUtils;
import org.jline.reader.History;
import org.jline.reader.impl.history.DefaultHistory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/** User: my5t3ry Date: 5/4/20 5:24 PM */
public class GoogleSearchCliHistory extends DefaultHistory implements History {
  private final List<String> controlCommands = new ArrayList<>();

  public GoogleSearchCliHistory() {
    CommandService.getCommands()
        .forEach(
            curCommand ->
                curCommand
                    .getCommands()
                    .forEach(curCommandString -> controlCommands.add(curCommandString)));
  }

  @Override
  public void add(Instant time, String line) {
    if (!controlCommands.contains(line) && !StringUtils.isNumeric(line)) {
      super.add(time, line);
    }
  }
}
