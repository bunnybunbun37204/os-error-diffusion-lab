/*
*MIT License

Copyright (c) 2024 Phongsakorn Phimphongphaisan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*
* */
package org.bunyawat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class CoreGraph {
    protected static void showCPUUsageChart(long[] cpuTimes) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Add CPU usage data to dataset
        for (int i = 0; i < cpuTimes.length; i++) {
            int count = i+1;
            System.out.println(cpuTimes[i]);
            dataset.addValue(cpuTimes[i], "CPU Time" + cpuTimes.length, "" + count);
        }

        // Create bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "CPU Usage during Error Diffusion",
                "CPU Cores",
                "CPU Time (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Show chart in a new window
        ChartPanel chartPanel = new ChartPanel(barChart);
        JFrame chartFrame = new JFrame("CPU Usage Chart");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(800, 600);
        chartFrame.add(chartPanel);
        chartFrame.setLocationRelativeTo(null);
        chartFrame.setVisible(true);
    }

}
