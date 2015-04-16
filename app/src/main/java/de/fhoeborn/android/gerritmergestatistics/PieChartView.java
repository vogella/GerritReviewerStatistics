package de.fhoeborn.android.gerritmergestatistics;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * This is a content class which has hard coded values for color and value variables that make up the slices in a pie
 * chart. At the moment this is only used to diplay the income, cost and sub total in the results view.
 *
 * @author Daniel Kvist
 *
 */
public class PieChartView extends GraphicalView
{

    public static final int COLOR_GREEN = Color.parseColor("#62c51a");
    public static final int COLOR_ORANGE = Color.parseColor("#ff6c0a");
    public static final int COLOR_BLUE = Color.parseColor("#23bae9");

    /**
     * Constructor that only calls the super method. It is not used to instantiate the object from outside of this
     * class.
     *
     * @param context
     * @param arg1
     */
    private PieChartView(Context context, AbstractChart arg1)
    {
        super(context, arg1);
    }


    public static GraphicalView getNewInstance(Context context, CategorySeries data)
    {
        return ChartFactory.getPieChartView(context, data, getRenderer(data.getItemCount()));
    }

    /**
     * Creates the renderer for the pie chart and sets the basic color scheme and hides labels and legend.
     *
     * @return a renderer for the pie chart
     */
    private static DefaultRenderer getRenderer(int count)
    {
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < count; i++)
        {
            SimpleSeriesRenderer simpleRenderer = new SimpleSeriesRenderer();
            if (i % 2 == 0) {
                simpleRenderer.setColor(COLOR_GREEN);
            }else{
                simpleRenderer.setColor(COLOR_BLUE);
            }
            defaultRenderer.addSeriesRenderer(simpleRenderer);
        }
        defaultRenderer.setShowLabels(false);
        defaultRenderer.setShowLegend(false);
        return defaultRenderer;
    }


}