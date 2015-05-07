package com.sec.factory.support;

import android.graphics.Color;
import android.graphics.Point;
import android.os.SystemProperties;
import java.io.File;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Support
{
  public static class CommandFilter
  {
    static ArrayList<String> filteredCommands = null;
    
    public static String[] getFilteredCommands()
    {
      if (filteredCommands != null)
      {
        if (filteredCommands.isEmpty() != true) {
          return (String[])filteredCommands.toArray(new String[0]);
        }
        return null;
      }
      try
      {
        Element[] arrayOfElement = XMLDataStorage.instance().getChildElementSet("CommandFilter");
        filteredCommands = new ArrayList();
        int i = arrayOfElement.length;
        for (int j = 0; j < i; j++)
        {
          Element localElement = arrayOfElement[j];
          boolean bool = Boolean.parseBoolean(localElement.getAttribute("enable"));
          String str1 = localElement.getAttribute("id");
          if ((bool) && (localElement.getNodeName().equals("item-group")))
          {
            NodeList localNodeList = localElement.getChildNodes();
            if (arrayOfElement != null) {
              for (int k = 0; k < localNodeList.getLength(); k++)
              {
                Node localNode = localNodeList.item(k);
                if (localNode.getNodeType() == 1)
                {
                  String str2 = localNode.getAttributes().getNamedItem("para").getNodeValue();
                  String str3 = localNode.getAttributes().getNamedItem("count").getNodeValue();
                  filteredCommands.add(str1 + "/" + str2 + "/" + str3);
                  FtUtil.log_d("Support", "CommandFilter", "filtername-" + str1 + "/" + str2 + "/" + str3);
                }
              }
            }
          }
        }
        if (!filteredCommands.isEmpty()) {
          break label326;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        filteredCommands = null;
        return null;
      }
      return null;
      label326:
      return (String[])filteredCommands.toArray(new String[0]);
    }
  }
  
  public static class FactoryTestMenu
  {
    public static String[] getFactoryTestInfo()
    {
      Element[] arrayOfElement = XMLDataStorage.instance().getChildElementSet(getFactoryTestMenuElemName());
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      String str1 = null;
      String str2 = null;
      String str3 = null;
      String str4 = null;
      int i = arrayOfElement.length;
      int j = 0;
      if (j < i)
      {
        Element localElement = arrayOfElement[j];
        boolean bool = Boolean.parseBoolean(localElement.getAttribute("enable"));
        FtUtil.log_v("Support", "getFact", "name=" + localElement.getAttribute("name") + ", enable=" + bool);
        if (bool)
        {
          str2 = localElement.getAttribute("name");
          str1 = localElement.getAttribute("action");
          int k;
          label199:
          Node localNode;
          String str5;
          String str6;
          String str7;
          if (localElement.getAttribute("extra") != null)
          {
            str4 = localElement.getAttribute("extra");
            if (!localElement.getNodeName().equals("item-group")) {
              break label529;
            }
            str3 = "0xFF";
            NodeList localNodeList = localElement.getChildNodes();
            if (arrayOfElement == null) {
              break label459;
            }
            k = 0;
            if (k >= localNodeList.getLength()) {
              break label449;
            }
            localNode = localNodeList.item(k);
            if ((localNode.getNodeType() == 1) && (Boolean.parseBoolean(((Element)localNode).getAttribute("enable"))))
            {
              str5 = localNode.getAttributes().getNamedItem("name").getNodeValue();
              str6 = localNode.getAttributes().getNamedItem("action").getNodeValue();
              str7 = localNode.getAttributes().getNamedItem("nv").getNodeValue();
              if (((Element)localNode).getAttribute("extra") == null) {
                break label442;
              }
            }
          }
          label442:
          for (String str8 = ((Element)localNode).getAttribute("extra") + "|invisibility";; str8 = "invisibility")
          {
            String str9 = str1;
            localArrayList2.add(str6 + "," + str5 + "," + str7 + "," + str8 + "," + str9 + ",");
            k++;
            break label199;
            str4 = "default";
            break;
          }
          label449:
          if (localArrayList2.isEmpty()) {
            bool = false;
          }
        }
        for (;;)
        {
          label459:
          if (bool) {
            localArrayList1.add(str1 + "," + str2 + "," + str3 + "," + str4 + "," + "null");
          }
          j++;
          break;
          label529:
          str3 = localElement.getAttribute("nv");
        }
      }
      localArrayList1.addAll(localArrayList2);
      return (String[])localArrayList1.toArray(new String[0]);
    }
    
    public static String getFactoryTestMenuElemName()
    {
      String str = "FactoryTestMenu";
      if (Support.Feature.getBoolean("SUPPORT_DUAL_LCD_FOLDER", false))
      {
        if (!FtUtil.isFolderOpen()) {
          str = str + "Sub";
        }
        FtUtil.log_d("Support", "getFactoryTestMenuElemName", "strFactoryTestMenu : " + str);
      }
      while (!Support.Feature.getBoolean("SUPPORT_DUALMODE", false)) {
        return str;
      }
      if ("XMM6262".equals(SystemProperties.get("ril.modem.board"))) {
        str = str + "Sub";
      }
      FtUtil.log_d("Support", "getFactoryTestMenuElemName", "strFactoryTestMenu : " + str);
      return str;
    }
    
    public static String getFactoryTestName(String paramString1, String paramString2)
    {
      String str = XMLDataStorage.instance().getAttributeValueByAttribute(paramString1, paramString2, "name");
      FtUtil.log_d("Support", "FactoryTestMenu.getFactoryTestName", "name=" + str);
      return str;
    }
    
    public static int getLogLevel(String paramString)
    {
      String str = XMLDataStorage.instance().getAttributeValueByAttribute("action", paramString, "loglevel");
      if ((str != null) && (!str.isEmpty())) {
        return Integer.parseInt(str);
      }
      return 1;
    }
    
    public static String getTestCase(String paramString)
    {
      return XMLDataStorage.instance().getAttributeValueByAttribute("action", paramString, "testcase");
    }
    
    public static float getUIRate(String paramString)
    {
      String str = XMLDataStorage.instance().getAttributeValueByAttribute("action", paramString, "uirate");
      if ((str != null) && (!str.isEmpty())) {
        return Float.parseFloat(str);
      }
      return 0.0F;
    }
  }
  
  public static class Feature
  {
    public static boolean getBoolean(String paramString)
    {
      return Support.Values.access$100(paramString, "value");
    }
    
    public static boolean getBoolean(String paramString, boolean paramBoolean)
    {
      return Support.Values.access$200(paramString, "value", true);
    }
    
    public static float getFactoryTextSize()
    {
      String str = XMLDataStorage.instance().getAttributeValueByTag(Support.FactoryTestMenu.getFactoryTestMenuElemName(), "fontsize");
      if ((str != null) && (!str.isEmpty())) {
        return Float.parseFloat(str);
      }
      return 0.0F;
    }
    
    public static int getInt(String paramString)
    {
      return Support.Values.access$400(paramString, "value");
    }
    
    public static String getString(String paramString)
    {
      return Support.Values.access$000(paramString, "value");
    }
  }
  
  public static class Kernel
  {
    public static long getFileLength(String paramString)
    {
      return new File(paramString).length();
    }
    
    public static String getFilePath(String paramString)
    {
      return Support.Values.access$000(paramString, "path");
    }
    
    public static boolean isExistFile(String paramString)
    {
      File localFile = new File(paramString);
      if (localFile != null) {
        return localFile.exists();
      }
      return false;
    }
    
    public static boolean isExistFileID(String paramString)
    {
      return new File(Support.Values.access$000(paramString, "path")).exists();
    }
    
    public static boolean mkDir(String paramString)
    {
      boolean bool = false;
      if (paramString != null)
      {
        int i = paramString.length();
        bool = false;
        if (i > 0)
        {
          File localFile = new File(paramString.substring(0, paramString.lastIndexOf("/")));
          if (localFile.exists()) {
            break label50;
          }
          bool = localFile.mkdir();
        }
      }
      return bool;
      label50:
      return true;
    }
    
    public static String read(String paramString)
    {
      return read(paramString, true);
    }
    
    /* Error */
    public static String read(String paramString, boolean paramBoolean)
    {
      // Byte code:
      //   0: iload_1
      //   1: ifeq +30 -> 31
      //   4: aload_0
      //   5: ldc 23
      //   7: invokestatic 29	com/sec/factory/support/Support$Values:access$000	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   10: astore_2
      //   11: aconst_null
      //   12: astore_3
      //   13: aconst_null
      //   14: astore 4
      //   16: aload_2
      //   17: ifnull +12 -> 29
      //   20: ldc 65
      //   22: aload_2
      //   23: invokevirtual 68	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
      //   26: ifeq +16 -> 42
      //   29: aconst_null
      //   30: areturn
      //   31: aload_0
      //   32: ldc 23
      //   34: iconst_0
      //   35: invokestatic 72	com/sec/factory/support/Support$Values:access$700	(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;
      //   38: astore_2
      //   39: goto -28 -> 11
      //   42: new 74	java/io/FileReader
      //   45: dup
      //   46: aload_2
      //   47: invokespecial 75	java/io/FileReader:<init>	(Ljava/lang/String;)V
      //   50: astore 5
      //   52: aconst_null
      //   53: astore 4
      //   55: aload 5
      //   57: ifnull +14 -> 71
      //   60: new 77	java/io/BufferedReader
      //   63: dup
      //   64: aload 5
      //   66: invokespecial 80	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
      //   69: astore 4
      //   71: aconst_null
      //   72: astore_3
      //   73: aload 4
      //   75: ifnull +22 -> 97
      //   78: aload 4
      //   80: invokevirtual 84	java/io/BufferedReader:readLine	()Ljava/lang/String;
      //   83: astore_3
      //   84: aload_3
      //   85: ifnull +12 -> 97
      //   88: aload_3
      //   89: invokevirtual 87	java/lang/String:trim	()Ljava/lang/String;
      //   92: astore 10
      //   94: aload 10
      //   96: astore_3
      //   97: aload 4
      //   99: ifnull +124 -> 223
      //   102: aload 4
      //   104: invokevirtual 90	java/io/BufferedReader:close	()V
      //   107: iload_1
      //   108: ifeq +38 -> 146
      //   111: ldc 92
      //   113: ldc 94
      //   115: new 96	java/lang/StringBuilder
      //   118: dup
      //   119: invokespecial 97	java/lang/StringBuilder:<init>	()V
      //   122: ldc 99
      //   124: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   127: aload_2
      //   128: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   131: ldc 105
      //   133: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   136: aload_3
      //   137: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   140: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   143: invokestatic 114	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   146: aload_3
      //   147: areturn
      //   148: astore 11
      //   150: aload 11
      //   152: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   155: goto -48 -> 107
      //   158: astore 6
      //   160: aload 6
      //   162: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   165: aload 4
      //   167: ifnull -60 -> 107
      //   170: aload 4
      //   172: invokevirtual 90	java/io/BufferedReader:close	()V
      //   175: goto -68 -> 107
      //   178: astore 9
      //   180: aload 9
      //   182: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   185: goto -78 -> 107
      //   188: astore 7
      //   190: aload 4
      //   192: ifnull +8 -> 200
      //   195: aload 4
      //   197: invokevirtual 90	java/io/BufferedReader:close	()V
      //   200: aload 7
      //   202: athrow
      //   203: astore 8
      //   205: aload 8
      //   207: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   210: goto -10 -> 200
      //   213: astore 7
      //   215: goto -25 -> 190
      //   218: astore 6
      //   220: goto -60 -> 160
      //   223: goto -116 -> 107
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	226	0	paramString	String
      //   0	226	1	paramBoolean	boolean
      //   10	118	2	str1	String
      //   12	135	3	localObject1	Object
      //   14	182	4	localBufferedReader	java.io.BufferedReader
      //   50	15	5	localFileReader	java.io.FileReader
      //   158	3	6	localException1	Exception
      //   218	1	6	localException2	Exception
      //   188	13	7	localObject2	Object
      //   213	1	7	localObject3	Object
      //   203	3	8	localIOException1	java.io.IOException
      //   178	3	9	localIOException2	java.io.IOException
      //   92	3	10	str2	String
      //   148	3	11	localIOException3	java.io.IOException
      // Exception table:
      //   from	to	target	type
      //   102	107	148	java/io/IOException
      //   42	52	158	java/lang/Exception
      //   170	175	178	java/io/IOException
      //   42	52	188	finally
      //   160	165	188	finally
      //   195	200	203	java/io/IOException
      //   60	71	213	finally
      //   78	84	213	finally
      //   88	94	213	finally
      //   60	71	218	java/lang/Exception
      //   78	84	218	java/lang/Exception
      //   88	94	218	java/lang/Exception
    }
    
    public static boolean setPermission(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
    {
      File localFile = new File(paramString);
      if ((localFile != null) && (localFile.exists()))
      {
        localFile.setExecutable(paramBoolean1, paramBoolean2);
        localFile.setWritable(paramBoolean3, paramBoolean4);
        localFile.setReadable(paramBoolean5, paramBoolean6);
        return true;
      }
      FtUtil.log_e("Support", "setPermission", "File not found : " + paramString);
      return false;
    }
    
    /* Error */
    public static boolean write(String paramString1, String paramString2)
    {
      // Byte code:
      //   0: aload_0
      //   1: ldc 23
      //   3: invokestatic 29	com/sec/factory/support/Support$Values:access$000	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   6: astore_2
      //   7: iconst_1
      //   8: istore_3
      //   9: aconst_null
      //   10: astore 4
      //   12: new 139	java/io/FileWriter
      //   15: dup
      //   16: aload_2
      //   17: invokespecial 140	java/io/FileWriter:<init>	(Ljava/lang/String;)V
      //   20: astore 5
      //   22: aload 5
      //   24: aload_1
      //   25: invokevirtual 142	java/io/FileWriter:write	(Ljava/lang/String;)V
      //   28: aload 5
      //   30: invokevirtual 145	java/io/FileWriter:flush	()V
      //   33: aload 5
      //   35: ifnull +8 -> 43
      //   38: aload 5
      //   40: invokevirtual 146	java/io/FileWriter:close	()V
      //   43: ldc 92
      //   45: ldc 148
      //   47: new 96	java/lang/StringBuilder
      //   50: dup
      //   51: invokespecial 97	java/lang/StringBuilder:<init>	()V
      //   54: ldc 99
      //   56: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   59: aload_2
      //   60: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   63: ldc 105
      //   65: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   68: aload_1
      //   69: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   72: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   75: invokestatic 114	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   78: iload_3
      //   79: ireturn
      //   80: astore 10
      //   82: aload 10
      //   84: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   87: iconst_0
      //   88: istore_3
      //   89: goto -46 -> 43
      //   92: astore 6
      //   94: aload 6
      //   96: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   99: iconst_0
      //   100: istore_3
      //   101: aload 4
      //   103: ifnull -60 -> 43
      //   106: aload 4
      //   108: invokevirtual 146	java/io/FileWriter:close	()V
      //   111: iconst_0
      //   112: istore_3
      //   113: goto -70 -> 43
      //   116: astore 9
      //   118: aload 9
      //   120: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   123: iconst_0
      //   124: istore_3
      //   125: goto -82 -> 43
      //   128: astore 7
      //   130: aload 4
      //   132: ifnull +8 -> 140
      //   135: aload 4
      //   137: invokevirtual 146	java/io/FileWriter:close	()V
      //   140: aload 7
      //   142: athrow
      //   143: astore 8
      //   145: aload 8
      //   147: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   150: goto -10 -> 140
      //   153: astore 7
      //   155: aload 5
      //   157: astore 4
      //   159: goto -29 -> 130
      //   162: astore 6
      //   164: aload 5
      //   166: astore 4
      //   168: goto -74 -> 94
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	171	0	paramString1	String
      //   0	171	1	paramString2	String
      //   6	54	2	str	String
      //   8	117	3	bool	boolean
      //   10	157	4	localObject1	Object
      //   20	145	5	localFileWriter	java.io.FileWriter
      //   92	3	6	localException1	Exception
      //   162	1	6	localException2	Exception
      //   128	13	7	localObject2	Object
      //   153	1	7	localObject3	Object
      //   143	3	8	localIOException1	java.io.IOException
      //   116	3	9	localIOException2	java.io.IOException
      //   80	3	10	localIOException3	java.io.IOException
      // Exception table:
      //   from	to	target	type
      //   38	43	80	java/io/IOException
      //   12	22	92	java/lang/Exception
      //   106	111	116	java/io/IOException
      //   12	22	128	finally
      //   94	99	128	finally
      //   135	140	143	java/io/IOException
      //   22	33	153	finally
      //   22	33	162	java/lang/Exception
    }
    
    /* Error */
    public static boolean write(String paramString, byte[] paramArrayOfByte)
    {
      // Byte code:
      //   0: aload_0
      //   1: ldc 23
      //   3: invokestatic 29	com/sec/factory/support/Support$Values:access$000	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   6: astore_2
      //   7: iconst_1
      //   8: istore_3
      //   9: aconst_null
      //   10: astore 4
      //   12: new 151	java/io/FileOutputStream
      //   15: dup
      //   16: aload_2
      //   17: invokespecial 152	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
      //   20: astore 5
      //   22: aload 5
      //   24: aload_1
      //   25: invokevirtual 155	java/io/FileOutputStream:write	([B)V
      //   28: aload 5
      //   30: invokevirtual 156	java/io/FileOutputStream:flush	()V
      //   33: aload 5
      //   35: ifnull +8 -> 43
      //   38: aload 5
      //   40: invokevirtual 157	java/io/FileOutputStream:close	()V
      //   43: ldc 92
      //   45: ldc 148
      //   47: new 96	java/lang/StringBuilder
      //   50: dup
      //   51: invokespecial 97	java/lang/StringBuilder:<init>	()V
      //   54: ldc 99
      //   56: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   59: aload_2
      //   60: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   63: ldc 105
      //   65: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   68: aload_1
      //   69: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   72: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   75: invokestatic 114	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   78: iload_3
      //   79: ireturn
      //   80: astore 10
      //   82: aload 10
      //   84: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   87: iconst_0
      //   88: istore_3
      //   89: goto -46 -> 43
      //   92: astore 6
      //   94: aload 6
      //   96: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   99: iconst_0
      //   100: istore_3
      //   101: aload 4
      //   103: ifnull -60 -> 43
      //   106: aload 4
      //   108: invokevirtual 157	java/io/FileOutputStream:close	()V
      //   111: iconst_0
      //   112: istore_3
      //   113: goto -70 -> 43
      //   116: astore 9
      //   118: aload 9
      //   120: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   123: iconst_0
      //   124: istore_3
      //   125: goto -82 -> 43
      //   128: astore 7
      //   130: aload 4
      //   132: ifnull +8 -> 140
      //   135: aload 4
      //   137: invokevirtual 157	java/io/FileOutputStream:close	()V
      //   140: aload 7
      //   142: athrow
      //   143: astore 8
      //   145: aload 8
      //   147: invokestatic 118	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
      //   150: goto -10 -> 140
      //   153: astore 7
      //   155: aload 5
      //   157: astore 4
      //   159: goto -29 -> 130
      //   162: astore 6
      //   164: aload 5
      //   166: astore 4
      //   168: goto -74 -> 94
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	171	0	paramString	String
      //   0	171	1	paramArrayOfByte	byte[]
      //   6	54	2	str	String
      //   8	117	3	bool	boolean
      //   10	157	4	localObject1	Object
      //   20	145	5	localFileOutputStream	java.io.FileOutputStream
      //   92	3	6	localException1	Exception
      //   162	1	6	localException2	Exception
      //   128	13	7	localObject2	Object
      //   153	1	7	localObject3	Object
      //   143	3	8	localIOException1	java.io.IOException
      //   116	3	9	localIOException2	java.io.IOException
      //   80	3	10	localIOException3	java.io.IOException
      // Exception table:
      //   from	to	target	type
      //   38	43	80	java/io/IOException
      //   12	22	92	java/lang/Exception
      //   106	111	116	java/io/IOException
      //   12	22	128	finally
      //   94	99	128	finally
      //   135	140	143	java/io/IOException
      //   22	33	153	finally
      //   22	33	162	java/lang/Exception
    }
  }
  
  public static class Properties
  {
    public static String get(String paramString)
    {
      String str = Support.Values.access$000(paramString, "key");
      FtUtil.log_d("Support", "Properties.get", "property=" + str);
      return SystemProperties.get(str, "Unknown");
    }
    
    public static void set(String paramString1, String paramString2)
    {
      String str = Support.Values.access$000(paramString1, "key");
      FtUtil.log_d("Support", "Properties.set", "property=" + str + ", value=" + paramString2);
      SystemProperties.set(str, paramString2);
    }
  }
  
  public static class Spec
  {
    public static int getInt(String paramString)
    {
      return Support.Values.access$400(paramString, "value");
    }
  }
  
  public static class SystemInfo
  {
    public static int getBGColor()
    {
      return Color.parseColor(Support.Values.access$000("SYS_INFO_BG_COLOR", "color"));
    }
    
    public static int getFontColor()
    {
      return Color.parseColor(Support.Values.access$000("SYS_INFO_FONT_COLOR", "color"));
    }
    
    public static int getFontSize()
    {
      return Support.Values.access$400("SYS_INFO_FONT_SIZE", "size");
    }
    
    public static String[] getPosition()
    {
      return Support.Values.access$000("SYS_INFO_OUT_POSITION", "point").split(",");
    }
    
    public static boolean getVisibility(String paramString)
    {
      return Support.Values.access$100(paramString, "visibility");
    }
  }
  
  public static class TestCase
  {
    public static boolean getEnabled(String paramString)
    {
      return Support.Values.access$100(paramString, "enable");
    }
    
    public static float getKeyTextSize(String paramString)
    {
      return Support.Values.access$500(paramString, "size");
    }
    
    public static float getSize(String paramString)
    {
      return Support.Values.access$500(paramString, "size");
    }
    
    public static String getString(String paramString)
    {
      return Support.Values.access$000(paramString, "value");
    }
    
    public static Point getViewPoint(String paramString)
    {
      String str = Support.Values.access$000(paramString, "point");
      Point localPoint = new Point();
      if (str != null)
      {
        String[] arrayOfString = str.split(",");
        localPoint.x = Integer.parseInt(arrayOfString[0]);
        localPoint.y = Integer.parseInt(arrayOfString[1]);
      }
      return localPoint;
    }
    
    public static boolean isTouchkey(String paramString)
    {
      return Support.Values.access$000(paramString, "keytype").equalsIgnoreCase("touch");
    }
  }
  
  private static class Values
  {
    private static boolean getBoolean(String paramString1, String paramString2)
    {
      return getBoolean(paramString1, paramString2, false);
    }
    
    private static boolean getBoolean(String paramString1, String paramString2, boolean paramBoolean)
    {
      boolean bool = paramBoolean;
      try
      {
        bool = Boolean.parseBoolean(XMLDataStorage.instance().getAttributeValueById(paramString1, paramString2));
        FtUtil.log_d("Support", "Values.getBoolean", "id=" + paramString1 + ", value=" + bool);
        return bool;
      }
      catch (Exception localException)
      {
        FtUtil.log_e(localException);
      }
      return bool;
    }
    
    private static float getFloat(String paramString1, String paramString2)
    {
      float f = 0.0F;
      try
      {
        f = Float.parseFloat(XMLDataStorage.instance().getAttributeValueById(paramString1, paramString2));
        FtUtil.log_d("Support", "Values.getFloat", "id=" + paramString1 + ", value=" + f);
        return f;
      }
      catch (Exception localException)
      {
        FtUtil.log_e(localException);
      }
      return f;
    }
    
    private static int getInt(String paramString1, String paramString2)
    {
      int i = 0;
      try
      {
        i = Integer.parseInt(XMLDataStorage.instance().getAttributeValueById(paramString1, paramString2));
        FtUtil.log_d("Support", "Values.getInt", "id=" + paramString1 + ", value=" + i);
        return i;
      }
      catch (Exception localException)
      {
        FtUtil.log_e(localException);
      }
      return i;
    }
    
    private static String getString(String paramString1, String paramString2)
    {
      return getString(paramString1, paramString2, true);
    }
    
    private static String getString(String paramString1, String paramString2, boolean paramBoolean)
    {
      String str = "Unknown";
      try
      {
        str = XMLDataStorage.instance().getAttributeValueById(paramString1, paramString2);
        if (paramBoolean) {
          FtUtil.log_d("Support", "Values.getString", "id=" + paramString1 + ", value=" + str);
        }
      }
      catch (Exception localException)
      {
        for (;;)
        {
          FtUtil.log_e(localException);
        }
      }
      if (str == null) {
        str = "Unknown";
      }
      return str;
    }
  }
  
  public static class Version
  {
    public static String getString(String paramString)
    {
      return Support.Values.getString(paramString, "value");
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.support.Support
 * JD-Core Version:    0.7.1
 */