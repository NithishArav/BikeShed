import javax.swing.table.AbstractTableModel;


class BikeTableModel extends AbstractTableModel
{
    private String[] columnNames;
    private Object[][] data;
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return (col == 6);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Class getColumnClass(int column)
    {
        return getValueAt(0, column).getClass();
    }

    public BikeTableModel(Object[][] data, String[] header)
    {
        this.data = data;
        this.columnNames = header;
    }
}