package com.gary.spiders.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CSVWordBankReader implements IWordBankReader
{
    private static final String CSV_DELIMITER = ",";
    private BufferedReader _br;
    public CSVWordBankReader(BufferedReader br)
    {
        _br = br;
    }

    public List<String> read()
    {
        List<String> rawWords = new ArrayList<String>();

        String line;
        try
        {
            while ((line = _br.readLine()) != null)
            {
                rawWords.addAll(Arrays.asList(line.split(CSV_DELIMITER)));
            }

        } catch (FileNotFoundException e)
        {
            //
        }
        catch (IOException e)
        {
            //
        }
        finally
        {
            if (_br != null)
            {
                try
                {
                    _br.close();
                }
                catch (IOException e)
                {
              //
                }
            }
        }
        //  Convert all of the words to upper case
        List<String> result = new ArrayList<String>(rawWords.size());
        Iterator<String> iter = rawWords.iterator();
        while(iter.hasNext())
        {
            result.add(iter.next().toUpperCase());
        }

        return result;
    }
}