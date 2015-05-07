package com.sec.factory.aporiented;

import android.util.Log;
import java.io.FileWriter;
import java.io.IOException;

public class NvHandler
{
  static NvHandler nvInstance;
  protected String nvList = null;
  
  private NvHandler()
  {
    if (this.nvList == null) {
      getFullTestNv();
    }
  }
  
  public static NvHandler getInstance()
  {
    try
    {
      if (nvInstance == null)
      {
        nvInstance = new NvHandler();
        Log.i("NvHandler", "nvHandler created");
      }
      return nvInstance;
    }
    finally {}
  }
  
  public String getFullHistNv()
  {
    return readOneLine("/efs/FactoryApp/hist_nv");
  }
  
  public String getFullTestNv()
  {
    this.nvList = readOneLine("/efs/FactoryApp/test_nv");
    if (this.nvList.length() != 120)
    {
      this.nvList = "01N02N03N04N05N06N07N08N09N10N11N12N13N14N15N16N17N18N19N20N21N22N23N24N25N26N27N28N29N30N31N32N33N34N35N36N37N38N39N40N";
      writeFile("/efs/FactoryApp/test_nv", this.nvList);
    }
    return this.nvList;
  }
  
  /* Error */
  protected String readOneLine(String paramString)
  {
    // Byte code:
    //   0: ldc 60
    //   2: astore_2
    //   3: aconst_null
    //   4: astore_3
    //   5: new 62	java/io/BufferedReader
    //   8: dup
    //   9: new 64	java/io/FileReader
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 67	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   17: sipush 8096
    //   20: invokespecial 70	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   23: astore 4
    //   25: aload 4
    //   27: ifnull +22 -> 49
    //   30: aload 4
    //   32: invokevirtual 73	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   35: astore_2
    //   36: aload_2
    //   37: ifnull +12 -> 49
    //   40: aload_2
    //   41: invokevirtual 76	java/lang/String:trim	()Ljava/lang/String;
    //   44: astore 12
    //   46: aload 12
    //   48: astore_2
    //   49: aload 4
    //   51: ifnull +130 -> 181
    //   54: aload 4
    //   56: invokevirtual 79	java/io/BufferedReader:close	()V
    //   59: aload_2
    //   60: ifnonnull +6 -> 66
    //   63: ldc 60
    //   65: astore_2
    //   66: aload_2
    //   67: areturn
    //   68: astore 5
    //   70: aload 5
    //   72: invokevirtual 82	java/io/IOException:printStackTrace	()V
    //   75: goto -16 -> 59
    //   78: astore 6
    //   80: aload 6
    //   82: invokevirtual 83	java/io/FileNotFoundException:printStackTrace	()V
    //   85: aload_3
    //   86: ifnull -27 -> 59
    //   89: aload_3
    //   90: invokevirtual 79	java/io/BufferedReader:close	()V
    //   93: goto -34 -> 59
    //   96: astore 9
    //   98: aload 9
    //   100: invokevirtual 82	java/io/IOException:printStackTrace	()V
    //   103: goto -44 -> 59
    //   106: astore 10
    //   108: aload 10
    //   110: invokevirtual 82	java/io/IOException:printStackTrace	()V
    //   113: aload_3
    //   114: ifnull -55 -> 59
    //   117: aload_3
    //   118: invokevirtual 79	java/io/BufferedReader:close	()V
    //   121: goto -62 -> 59
    //   124: astore 11
    //   126: aload 11
    //   128: invokevirtual 82	java/io/IOException:printStackTrace	()V
    //   131: goto -72 -> 59
    //   134: astore 7
    //   136: aload_3
    //   137: ifnull +7 -> 144
    //   140: aload_3
    //   141: invokevirtual 79	java/io/BufferedReader:close	()V
    //   144: aload 7
    //   146: athrow
    //   147: astore 8
    //   149: aload 8
    //   151: invokevirtual 82	java/io/IOException:printStackTrace	()V
    //   154: goto -10 -> 144
    //   157: astore 7
    //   159: aload 4
    //   161: astore_3
    //   162: goto -26 -> 136
    //   165: astore 10
    //   167: aload 4
    //   169: astore_3
    //   170: goto -62 -> 108
    //   173: astore 6
    //   175: aload 4
    //   177: astore_3
    //   178: goto -98 -> 80
    //   181: goto -122 -> 59
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	184	0	this	NvHandler
    //   0	184	1	paramString	String
    //   2	65	2	localObject1	Object
    //   4	174	3	localObject2	Object
    //   23	153	4	localBufferedReader	java.io.BufferedReader
    //   68	3	5	localIOException1	IOException
    //   78	3	6	localFileNotFoundException1	java.io.FileNotFoundException
    //   173	1	6	localFileNotFoundException2	java.io.FileNotFoundException
    //   134	11	7	localObject3	Object
    //   157	1	7	localObject4	Object
    //   147	3	8	localIOException2	IOException
    //   96	3	9	localIOException3	IOException
    //   106	3	10	localIOException4	IOException
    //   165	1	10	localIOException5	IOException
    //   124	3	11	localIOException6	IOException
    //   44	3	12	str	String
    // Exception table:
    //   from	to	target	type
    //   54	59	68	java/io/IOException
    //   5	25	78	java/io/FileNotFoundException
    //   89	93	96	java/io/IOException
    //   5	25	106	java/io/IOException
    //   117	121	124	java/io/IOException
    //   5	25	134	finally
    //   80	85	134	finally
    //   108	113	134	finally
    //   140	144	147	java/io/IOException
    //   30	36	157	finally
    //   40	46	157	finally
    //   30	36	165	java/io/IOException
    //   40	46	165	java/io/IOException
    //   30	36	173	java/io/FileNotFoundException
    //   40	46	173	java/io/FileNotFoundException
  }
  
  public boolean setTestNv(String paramString1, String paramString2)
  {
    int i = Integer.valueOf(paramString1).intValue();
    Log.i("NvHandler", "id : " + paramString1 + ", result : " + paramString2);
    String str1 = paramString2.toUpperCase();
    boolean bool1;
    if ((!str1.equalsIgnoreCase("P")) && (!str1.equalsIgnoreCase("N")) && (!str1.equalsIgnoreCase("F")))
    {
      boolean bool2 = str1.equalsIgnoreCase("E");
      bool1 = false;
      if (!bool2) {}
    }
    else
    {
      if ((i <= 0) || (i >= 41)) {
        break label226;
      }
      String str6 = this.nvList.substring(0, 2 + 3 * (i - 1));
      String str7 = this.nvList.substring(i * 3, this.nvList.length());
      this.nvList = (str6 + str1 + str7);
      writeFile("/efs/FactoryApp/test_nv", this.nvList);
      String str8 = readOneLine("/efs/FactoryApp/hist_nv");
      writeFile("/efs/FactoryApp/hist_nv", str8 + paramString1 + str1);
      bool1 = true;
    }
    label226:
    do
    {
      return bool1;
      if (i == 98)
      {
        String str4 = this.nvList.substring(0, 9);
        this.nvList = (str4 + "04N05N06N07N08N09N10N11N12N13N14N15N16N17N18N19N20N21N22N23N24N25N26N27N28N29N30N31N32N33N34N35N36N37N38N39N40N");
        writeFile("/efs/FactoryApp/test_nv", this.nvList);
        String str5 = readOneLine("/efs/FactoryApp/hist_nv");
        writeFile("/efs/FactoryApp/hist_nv", str5 + paramString1 + str1);
        return true;
      }
      bool1 = false;
    } while (i != 99);
    String str2 = this.nvList.substring(0, 18);
    this.nvList = (str2 + "07N08N09N10N11N12N13N14N15N16N17N18N19N20N21N22N23N24N25N26N27N28N29N30N31N32N33N34N35N36N37N38N39N40N");
    writeFile("/efs/FactoryApp/test_nv", this.nvList);
    String str3 = readOneLine("/efs/FactoryApp/hist_nv");
    writeFile("/efs/FactoryApp/hist_nv", str3 + paramString1 + str1);
    return true;
  }
  
  protected boolean writeFile(String paramString1, String paramString2)
  {
    localObject = null;
    do
    {
      try
      {
        localFileWriter = new FileWriter(paramString1);
        boolean bool = false;
      }
      catch (IOException localIOException3)
      {
        try
        {
          localFileWriter.write(paramString2);
          localFileWriter.flush();
          localFileWriter.close();
          bool = true;
          return bool;
        }
        catch (IOException localIOException1)
        {
          FileWriter localFileWriter;
          localObject = localFileWriter;
          continue;
        }
        localIOException3 = localIOException3;
      }
    } while (localObject == null);
    try
    {
      ((FileWriter)localObject).close();
      return false;
    }
    catch (IOException localIOException2)
    {
      return false;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.NvHandler
 * JD-Core Version:    0.7.1
 */