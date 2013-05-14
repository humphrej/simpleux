package com.specialprojectslab.simpleux.grid;

import javax.swing.*;

public interface FilterUXStrategy {

    JComponent makeFilter(SimpleUXTable dataTable, JScrollPane dataTableScrollPane) ;
}
