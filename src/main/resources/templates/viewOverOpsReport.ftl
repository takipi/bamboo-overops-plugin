<head xmlns="http://www.w3.org/1999/html">
    <meta name="tab" content="overopsQualityReport"/>
</head>

<body>
<style>
    .overops-report table, .overops-report th, .overops-report td {
        border: 1px solid black;
        border-collapse: collapse;
    }

    .overops-report th, .overops-report td {
        padding: 5px;
        text-align: left;
    }

    .failed {
        color: #ff0000;
    }

    .passed {
        color: #008000;
    }

    .orange {
        color: #FFA500;
    }
</style>
<div class="overops-report">
    <h1>OverOps Quality Report</h1>
    [#if report.markedUnstable ]
        [#if report.unstable]
            <div>
                <h2 class="failed">${report.summary}</h2>
            </div>
        [#else]
            <div>
                <h2 class="passed">${report.summary}</h2>
            </div>
        [/#if]
    [/#if]

    [#if !report.markedUnstable ]
        [#if report.unstable]
        <div>
            <h2 class="orange">${report.summary}</h2>
        </div>
        [#else]
        <div>
            <h2 class="passed">${report.summary}</h2>
        </div>
        [/#if]
    [/#if]

    </br>
    [#if report.checkNewEvents ]
        <table style="width:100%">
            [#if report.passedNewErrorGate]
                <tr style="font-weight:bold">
                    <td style="color:#008000">${report.newErrorSummary}</td>
                </tr>
                <table style="width:100%">
                    <tr>
                        <td>Nothing to report</td>
                    </tr>
                </table>
            [#else]
                <tr style="font-weight:bold">
                    <td style="color:#ff0000">${report.newErrorSummary}</td>
                </tr>
                    <table style="width:100%">
                        <tr style="font-weight:bold">
                            <td>Event</td>
                            <td>Application(s)</td>
                            <td>Introduced by</td>
                            <td>Volume</td>
                        </tr>
                         [#foreach k in report.newEvents]
                            <tr>
                                <td><a href="${k.arcLink}" target="_blank"> ${k.eventSummary}</a></td>
                                <td>${k.applications}</td>
                                <td>${k.introducedBy}</td>
                                <td>${k.hits}</td>
                            </tr>
                         [/#foreach]
                    </table>
            [/#if]
        </table>
    [/#if]
    </br>
    [#if report.checkResurfacedEvents ]
        <table style="width:100%">
            [#if report.passedResurfacedErrorGate]
                <tr style="font-weight:bold">
                    <td style="color:#008000">${report.resurfacedErrorSummary}</td>
                </tr>
                <table style="width:100%">
                    <tr>
                        <td>Nothing to report</td>
                    </tr>
                </table>
            [#else]
                <tr style="font-weight:bold">
                    <td style="color:#ff0000">${report.resurfacedErrorSummary}</td>
                </tr>
                    <table style="width:100%">
                        <tr style="font-weight:bold">
                            <td>Event</td>
                            <td>Application(s)</td>
                            <td>Introduced by</td>
                            <td>Volume</td>
                        </tr>
                         [#foreach k in report.resurfacedEvents]
                            <tr>
                                <td><a href="${k.arcLink}" target="_blank"> ${k.eventSummary}</a></td>
                                <td>${k.applications}</td>
                                <td>${k.introducedBy}</td>
                                <td>${k.hits}</td>
                            </tr>
                         [/#foreach]
                    </table>
            [/#if]
        </table>
    [/#if]
    </br>
    [#if report.checkTotalErrors || report.checkUniqueErrors ]
        <table style="width:100%">
            [#if report.checkTotalErrors ]
                [#if report.passedTotalErrorGate]
                    <tr style="font-weight:bold">
                        <td style="color:#008000">${report.totalErrorSummary}</td>
                    </tr>
                [#else]
                   <tr style="font-weight:bold">
                       <td style="color:#ff0000">${report.totalErrorSummary}</td>
                   </tr>
                [/#if]
            [/#if]
            [#if report.checkUniqueErrors ]
                [#if report.passedUniqueErrorGate]
                    <tr style="font-weight:bold">
                        <td style="color:#008000">${report.uniqueErrorSummary}</td>
                    </tr>
                [#else]
                   <tr style="font-weight:bold">
                       <td style="color:#ff0000">${report.uniqueErrorSummary}</td>
                   </tr>
                [/#if]
            [/#if]
            [#if report.hasTopErrors]
                    <table style="width:100%">
                        <tr style="font-weight:bold">
                            <td>Top Events Affecting Unique/Total Error Gates</td>
                            <td>Application(s)</td>
                            <td>Introduced by</td>
                            <td>Volume</td>
                        </tr>
                        [#foreach k in report.topEvents]
                            <tr>
                                <td><a href="${k.arcLink}" target="_blank"> ${k.eventSummary}</a></td>
                                <td>${k.applications}</td>
                                <td>${k.introducedBy}</td>
                                <td>${k.hits}</td>
                            </tr>
                        [/#foreach]
                    </table>
            [#else]
                   <table style="width:100%">
                       <tr>
                           <td>Nothing to report</td>
                       </tr>
                   </table>
            [/#if]
        </table>
    [/#if]
    </br>
    [#if report.checkCriticalErrors ]
        <table style="width:100%">
            [#if report.passedCriticalErrorGate]
                <tr style="font-weight:bold">
                    <td style="color:#008000">${report.criticalErrorSummary}</td>
                </tr>
                <table style="width:100%">
                    <tr>
                        <td>Nothing to report</td>
                    </tr>
                </table>
            [#else]
                <tr style="font-weight:bold">
                    <td style="color:#ff0000">${report.criticalErrorSummary}</td>
                </tr>
                    <table style="width:100%">
                        <tr style="font-weight:bold">
                            <td>Event</td>
                            <td>Application(s)</td>
                            <td>Introduced by</td>
                            <td>Volume</td>
                        </tr>
                         [#foreach k in report.criticalEvents]
                            <tr>
                                <td><a href="${k.arcLink}" target="_blank"> ${k.eventSummary}</a></td>
                                <td>${k.applications}</td>
                                <td>${k.introducedBy}</td>
                                <td>${k.hits}</td>
                            </tr>
                         [/#foreach]
                    </table>
            [/#if]
        </table>
    [/#if]
    </br>
    [#if report.checkRegressedErrors ]
        <table style="width:100%">
            [#if report.passedRegressedEvents]
                <tr style="font-weight:bold">
                    <td style="color:#008000">${report.regressionSumarry}</td>
                </tr>
                <table style="width:100%">
                    <tr>
                        <td>Nothing to report</td>
                    </tr>
                </table>
            [#else]
                <tr style="font-weight:bold">
                    <td style="color:#ff0000">${report.regressionSumarry}</td>
                </tr>
                    <table style="width:100%">
                        <tr style="font-weight:bold">
                            <td>Event</td>
                            <td>Application(s)</td>
                            <td>Introduced by</td>
                            <td>Volume</td>
                        </tr>
                         [#foreach k in report.regressedEvents]
                            <tr>
                                <td><a href="${k.arcLink}" target="_blank"> ${k.eventSummary}</a></td>
                                <td>${k.applications}</td>
                                <td>${k.introducedBy}</td>
                                <td>${k.hits}</td>
                            </tr>
                         [/#foreach]
                    </table>
            [/#if]
        </table>
    [/#if]

</div>
</body>