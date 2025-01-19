import java.awt.*;


/**
 * Helper class for all Component Classes
 * Used to simplify GrigBagLayouts
 */
class ComposedConstraint
    {
        static GridBagConstraints self;

        public ComposedConstraint()
        {
            self = new GridBagConstraints();
            self.anchor = GridBagConstraints.NORTH;
        }
        
        public GridBagConstraints get(int gridx, int gridy)
        {
            return get(gridx, gridy, 1);
        }

        public GridBagConstraints get(int gridx, int gridy, int gridwidth)
        {
            self.gridx = gridx;
            self.gridy = gridy;
            self.gridwidth = gridwidth;
            return self;
        }
    }