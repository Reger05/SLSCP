@echo off
javac src/com/eger/slscp/*.java
jar cvfm slscp-Analyzer.jar Manifest.txt -C src com/eger/slscp/Plan.class -C src com/eger/slscp/State.class -C src com/eger/slscp/RateArea.class -C src com/eger/slscp/SLSCPAnalyzer.class -C src com/eger/slscp/Plan$PlanLevel.class -C resources com/eger/slscp/plans.csv -C resources com/eger/slscp/zip.csv -C resources com/eger/slscp/slscp.csv