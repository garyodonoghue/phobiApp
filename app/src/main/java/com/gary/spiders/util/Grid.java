package com.gary.spiders.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the grid of characters that makes up the word search puzzle
 *  Grid
 *
 */
public class Grid
{
    private char[][] _char_matrix;
    private int _x_size;
    private int _y_size;

    public static final char BLANK_CHAR = '0';

    public Grid(int x, int y)
    {
        _x_size = x;
        _y_size = y;
        generateBlankGrid();
    }

    /**
     * Generates our starting grid, filling it with a pre-defined "blank" character.
     * @return
     */
    protected void generateBlankGrid()
    {
        _char_matrix = new char[_x_size][_y_size];
        for(int i = 0; i < _y_size; i++)
        {
            for(int j = 0; j < _x_size; j++)
            {
                _char_matrix[i][j] = BLANK_CHAR;
            }
        }
    }

    public String[] getGridArray()
    {
        List<String> letters = new ArrayList<>();
        for(int i = 0; i < _y_size; i++)
        {
            for(int j = 0; j < _x_size; j++)
            {
                letters.add(String.valueOf(_char_matrix[j][i]));
            }
        }
        return letters.toArray(new String[0]);
    }

    public void setCharAt(GridCoordinate gc, char input)
    {
        _char_matrix[gc.getX()][gc.getY()] = input;
    }

    public char getCharAt(GridCoordinate gc)
    {
        return _char_matrix[gc.getX()][gc.getY()];
    }

    public char getBlankChar()
    {
        return BLANK_CHAR;
    }

    public int getXSize()
    {
        return _x_size;
    }

    public int getYSize()
    {
        return _y_size;
    }
}