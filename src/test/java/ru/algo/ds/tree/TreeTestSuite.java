package ru.algo.ds.tree;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectPackages({"ru.algo.ds.tree.btree", "ru.algo.ds.tree.trie"})
@IncludeTags("tree")
@SuiteDisplayName("A Tree Test Suite")
@Suite
public class TreeTestSuite {
}