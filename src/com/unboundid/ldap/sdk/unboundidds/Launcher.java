/*
 * Copyright 2010-2017 UnboundID Corp.
 * All Rights Reserved.
 */
/*
 * Copyright (C) 2015-2017 UnboundID Corp.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPLv2 only)
 * or the terms of the GNU Lesser General Public License (LGPLv2.1 only)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 */
package com.unboundid.ldap.sdk.unboundidds;



import java.io.OutputStream;
import java.io.PrintStream;

import com.unboundid.ldap.listener.InMemoryDirectoryServerTool;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.Version;
import com.unboundid.ldap.sdk.examples.AuthRate;
import com.unboundid.ldap.sdk.examples.IdentifyReferencesToMissingEntries;
import com.unboundid.ldap.sdk.examples.IdentifyUniqueAttributeConflicts;
import com.unboundid.ldap.sdk.examples.LDAPCompare;
import com.unboundid.ldap.sdk.examples.LDAPDebugger;
import com.unboundid.ldap.sdk.examples.ModRate;
import com.unboundid.ldap.sdk.examples.SearchRate;
import com.unboundid.ldap.sdk.examples.SearchAndModRate;
import com.unboundid.ldap.sdk.examples.ValidateLDIF;
import com.unboundid.ldap.sdk.persist.GenerateSchemaFromSource;
import com.unboundid.ldap.sdk.persist.GenerateSourceFromSchema;
import com.unboundid.ldap.sdk.unboundidds.examples.DumpDNs;
import com.unboundid.ldap.sdk.unboundidds.examples.SubtreeAccessibility;
import com.unboundid.ldap.sdk.unboundidds.examples.SummarizeAccessLog;
import com.unboundid.ldap.sdk.unboundidds.tools.LDAPModify;
import com.unboundid.ldap.sdk.unboundidds.tools.LDAPSearch;
import com.unboundid.ldap.sdk.unboundidds.tools.ManageAccount;
import com.unboundid.ldap.sdk.unboundidds.tools.SplitLDIF;
import com.unboundid.util.StaticUtils;
import com.unboundid.util.ThreadSafety;
import com.unboundid.util.ThreadSafetyLevel;



/**
 * This class provides an entry point that may be used to launch other tools
 * provided as part of the LDAP SDK.  This is primarily a convenience for
 * someone who just has the jar file and none of the scripts, since you can run
 * "<CODE>java -jar unboundid-ldapsdk-se.jar {tool-name} {tool-args}</CODE>"
 * in order to invoke any of the example tools.  Running just
 * "<CODE>java -jar unboundid-ldapsdk-se.jar</CODE>" will display version
 * information about the LDAP SDK.
 * <BR>
 * <BLOCKQUOTE>
 *   <B>NOTE:</B>  This class is part of the Commercial Edition of the UnboundID
 *   LDAP SDK for Java.  It is not available for use in applications that
 *   include only the Standard Edition of the LDAP SDK, and is not supported for
 *   use in conjunction with non-UnboundID products.
 * </BLOCKQUOTE>
 * <BR>
 * The tool names are case-insensitive.  Supported tool names include:
 * <UL>
 *   <LI>authrate -- Launch the {@link AuthRate} tool.</LI>
 *   <LI>deliver-one-time-password -- Launch the
 *       {@link DeliverOneTimePassword} tool.</LI>
 *   <LI>deliver-password-reset-token -- Launch the
 *       {@link DeliverPasswordResetToken} tool.</LI>
 *   <LI>dump-dns -- Launch the {@link DumpDNs} tool.</LI>
 *   <LI>generate-schema-from-source -- Launch the
 *       {@link GenerateSchemaFromSource} tool.</LI>
 *   <LI>generate-source-from-schema -- Launch the
 *       {@link GenerateSourceFromSchema} tool.</LI>
 *   <LI>identify-references-to-missing-entries -- Launch the
 *       {@link IdentifyReferencesToMissingEntries} tool.</LI>
 *   <LI>identify-unique-attribute-conflicts -- Launch the
 *       {@link IdentifyUniqueAttributeConflicts} tool.</LI>
 *   <LI>in-memory-directory-server -- Launch the
 *       {@link InMemoryDirectoryServerTool} tool.</LI>
 *   <LI>ldapcompare -- Launch the {@link LDAPCompare} tool.</LI>
 *   <LI>ldapmodify -- Launch the {@link LDAPModify} tool.</LI>
 *   <LI>ldapsearch -- Launch the {@link LDAPSearch} tool.</LI>
 *   <LI>ldap-debugger -- Launch the {@link LDAPDebugger} tool.</LI>
 *   <LI>manage-account -- Launch the {@link ManageAccount} tool.</LI>
 *   <LI>modrate -- Launch the {@link ModRate} tool.</LI>
 *   <LI>move-subtree -- Launch the {@link MoveSubtree} tool.</LI>
 *   <LI>register-yubikey-otp-device -- Launch the
 *       {@link RegisterYubiKeyOTPDevice} tool.</LI>
 *   <LI>searchrate -- Launch the {@link SearchRate} tool.</LI>
 *   <LI>search-and-mod-rate -- Launch the {@link SearchAndModRate} tool.</LI>
 *   <LI>split-ldif -- Launch the {@link SplitLDIF} tool.</LI>
 *   <LI>subtree-accessibility -- Launch the {@link SubtreeAccessibility}
 *       tool.</LI>
 *   <LI>summarize-access-log -- Launch the {@link SummarizeAccessLog}
 *       tool.</LI>
 *   <LI>validate-ldif -- Launch the {@link ValidateLDIF} tool.</LI>
 *   <LI>version -- Display version information for the LDAP SDK.</LI>
 * </UL>
 */
@ThreadSafety(level=ThreadSafetyLevel.COMPLETELY_THREADSAFE)
public final class Launcher
{
  /**
   * Prevent this utility class from being instantiated.
   */
  Launcher()
  {
    // No implementation required.
  }



  /**
   * Parses the command-line arguments and performs any appropriate processing
   * for this program.
   *
   * @param  args  The command-line arguments provided to this program.
   */
  public static void main(final String... args)
  {
    main(System.out, System.err, args);
  }



  /**
   * Parses the command-line arguments and performs any appropriate processing
   * for this program.
   *
   * @param  outStream  The output stream to which standard out should be
   *                    written.  It may be {@code null} if output should be
   *                    suppressed.
   * @param  errStream  The output stream to which standard error should be
   *                    written.  It may be {@code null} if error messages
   *                    should be suppressed.
   * @param  args       The command-line arguments provided to this program.
   *
   * @return  A result code with information about the status of processing.
   */
  public static ResultCode main(final OutputStream outStream,
                                final OutputStream errStream,
                                final String... args)
  {
    if ((args == null) || (args.length == 0) ||
        args[0].equalsIgnoreCase("version"))
    {
      if (outStream != null)
      {
        final PrintStream out = new PrintStream(outStream);
        for (final String line : Version.getVersionLines())
        {
          out.println(line);
        }
      }

      return ResultCode.SUCCESS;
    }

    final String firstArg = StaticUtils.toLowerCase(args[0]);
    final String[] remainingArgs = new String[args.length - 1];
    System.arraycopy(args, 1, remainingArgs, 0, remainingArgs.length);

    if (firstArg.equals("authrate"))
    {
      return AuthRate.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("deliver-one-time-password"))
    {
      return DeliverOneTimePassword.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("deliver-password-reset-token"))
    {
      return DeliverPasswordResetToken.main(remainingArgs, outStream,
           errStream);
    }
    else if (firstArg.equals("dump-dns"))
    {
      return DumpDNs.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("identify-references-to-missing-entries"))
    {
      return IdentifyReferencesToMissingEntries.main(remainingArgs, outStream,
           errStream);
    }
    else if (firstArg.equals("identify-unique-attribute-conflicts"))
    {
      return IdentifyUniqueAttributeConflicts.main(remainingArgs, outStream,
           errStream);
    }
    else if (firstArg.equals("in-memory-directory-server"))
    {
      return InMemoryDirectoryServerTool.main(remainingArgs, outStream,
           errStream);
    }
    else if (firstArg.equals("generate-schema-from-source"))
    {
      return GenerateSchemaFromSource.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("generate-source-from-schema"))
    {
      return GenerateSourceFromSchema.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("ldapcompare"))
    {
      return LDAPCompare.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("ldapmodify"))
    {
      return LDAPModify.main(System.in, outStream, errStream, remainingArgs);
    }
    else if (firstArg.equals("ldapsearch"))
    {
      return LDAPSearch.main(outStream, errStream, remainingArgs);
    }
    else if (firstArg.equals("ldap-debugger"))
    {
      return LDAPDebugger.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("manage-account"))
    {
      return ManageAccount.main(outStream, errStream, remainingArgs);
    }
    else if (firstArg.equals("modrate"))
    {
      return ModRate.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("move-subtree"))
    {
      return MoveSubtree.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("register-yubikey-otp-device"))
    {
      return RegisterYubiKeyOTPDevice.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("searchrate"))
    {
      return SearchRate.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("search-and-mod-rate"))
    {
      return SearchAndModRate.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("split-ldif"))
    {
      return SplitLDIF.main(outStream, errStream, remainingArgs);
    }
    else if (firstArg.equals("subtree-accessibility"))
    {
      return SubtreeAccessibility.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("summarize-access-log"))
    {
      return SummarizeAccessLog.main(remainingArgs, outStream, errStream);
    }
    else if (firstArg.equals("validate-ldif"))
    {
      return ValidateLDIF.main(remainingArgs, outStream, errStream);
    }
    else
    {
      if (errStream != null)
      {
        final PrintStream err = new PrintStream(errStream);
        err.println("Unrecognized tool name '" + args[0] + '\'');
        err.println("Supported tool names include:");
        err.println("     authrate");
        err.println("     deliver-one-time-password");
        err.println("     deliver-password-reset-token");
        err.println("     dump-dns");
        err.println("     identify-references-to-missing-entries");
        err.println("     identify-unique-attribute-conflicts");
        err.println("     in-memory-directory-server");
        err.println("     generate-schema-from-source");
        err.println("     generate-source-from-schema");
        err.println("     ldapcompare");
        err.println("     ldapmodify");
        err.println("     ldapsearch");
        err.println("     ldap-debugger");
        err.println("     manage-account");
        err.println("     modrate");
        err.println("     move-subtree");
        err.println("     register-yubikey-otp-device");
        err.println("     searchrate");
        err.println("     search-and-mod-rate");
        err.println("     split-ldif");
        err.println("     subtree-accessibility");
        err.println("     summarize-access-log");
        err.println("     validate-ldif");
        err.println("     version");
      }

      return ResultCode.PARAM_ERROR;
    }
  }
}
