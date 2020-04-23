<style>
    details.quality-gate-options {
        --summary-offset: 21px;

        margin-left: var(--summary-offset);
    }

    details.quality-gate-options > summary {
        margin-left: calc(-1 * var(--summary-offset));
    }

    details.quality-gate-options > summary::-webkit-details-marker {
        display: none;
    }

    details[open].quality-gate-options > summary ~ * {
        animation: sweep .3s ease-in-out;
    }

    @keyframes sweep {
        0%    {opacity: 0; margin-left: -10px}
        100%  {opacity: 1; margin-left: 0px}
    }

    hr.divider {
        border-top: 1px solid #dfe1e5;
        border-bottom: 0;
        border-right: 0;
        border-left: 0;
    }
    code.inline {
        display: inline;
        padding: 0 0.25em;
        margin: 0;
        background: none;
        border: 1px dashed #dfe1e5;
        color: #6b778c;
    }
    .text.errorField {
        border-color: #de350b !important;
    }
</style>

<h3>General settings</h3>
[@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.envId" name="envId" required="false" description="The OverOps environment identifier (e.g S12345). If blank, the <a href=\"/plugins/servlet/overops/admin\">default value</a> is used."/]
[@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.applicationName" name="applicationName" required="false" description="<em>(Optional)</em> Application Name as specified in OverOps. If blank, a Deplyment Name must be provided."/]
[@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.deploymentName" name="deploymentName" required="false" description="<em>(Optional)</em> Deployment Name as specified in OverOps. If blank, an Application Name must be provided. See: <a href='https://confluence.atlassian.com/bamboo/bamboo-variables-289277087.html' target='_blank' rel='nofollow'>Bamboo variables</a>"/]
<hr class="divider"/>
[@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.regexFilter" name="regexFilter" required="false" description="<em>(Optional)</em> Ignore specific event types when generating the OverOps Reliability report. Event types include: <em>Uncaught Exception, Caught Exception, Swallowed Exception, Logged Error, Logged Warning, Timer</em>. For example: <code class='inline'>\"type\":\"s*(Logged Error|Logged Warning|Timer)\"</code>" /]
<hr class="divider"/>
[@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.topErrorCount" name="topErrorCount" required="false" description="The number of total and unique errors to show in the quality report, sorted by volume." /]
<hr class="divider"/>
[@ww.checkbox labelKey="com.overops.plugins.bamboo.task.config.markUnstable" name="markUnstable" required="false" description="If checked the build will be marked unstable when any quality gates fail."/]

<h3>Quality Gate settings</h3>
[@ww.checkbox labelKey="com.overops.plugins.bamboo.task.config.checkNewErrors" name="checkNewErrors" required="false" description="Check if the current build has any new errors." /]
<hr class="divider"/>
[@ww.checkbox labelKey="com.overops.plugins.bamboo.task.config.checkResurfacedErrors" name="checkResurfacedErrors" required="false" description="Check if the current build has any errors that have resurfaced since being marked as resolved in OverOps." /]

<hr class="divider"/>
<details class="quality-gate-options" [#if checkVolumeErrors = "true"] open [/#if]>
    <summary>
        [@ww.checkbox labelKey="com.overops.plugins.bamboo.task.config.checkVolumeErrors" name="checkVolumeErrors" class="checkbox toggle-details" required="false" tabindex="-1" description="Use this Gate to limit the Total number of allowable Errors in the current build."/]
    </summary>
    [@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.maxErrorVolume" name="maxErrorVolume" required="false" description="Set the max total error volume allowed. If exceeded the build will be marked as unstable."/]
</details>

<hr class="divider"/>
<details class="quality-gate-options" [#if checkUniqueErrors = "true"] open [/#if]>
    <summary>
        [@ww.checkbox labelKey="com.overops.plugins.bamboo.task.config.checkUniqueErrors" name="checkUniqueErrors" class="checkbox toggle-details" required="false" tabindex="-1" description="Use this Gate to limit the number of allowable Unique Errors in the current build."/]
    </summary>
    [@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.maxUniqueErrors" name="maxUniqueErrors" required="false" description="Set the max unique error count allowed. If the target volume is exceeded the build will be marked as unstable."/]
</details>

<hr class="divider"/>
<details class="quality-gate-options" [#if checkCriticalErrors = "true"] open [/#if]>
    <summary>
        [@ww.checkbox labelKey="com.overops.plugins.bamboo.task.config.checkCriticalErrors" name="checkCriticalErrors" class="checkbox toggle-details" required="false" tabindex="-1" description="Use this Gate to detect specific critical errors in the current build."/]
    </summary>
    [@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.criticalExceptionTypes" name="criticalExceptionTypes" required="false" description="A comma delimited list of critical exception types. For example: <code class='inline'>NullPointerException,IndexOutOfBoundsException</code>"/]
</details>

<hr class="divider"/>

<details class="quality-gate-options" [#if checkIncreasingErrors = "true"] open [/#if]>
    <summary>
        [@ww.checkbox labelKey="com.overops.plugins.bamboo.task.config.checkIncreasingErrors" name="checkIncreasingErrors" class="checkbox toggle-details" required="false" tabindex="-1" description="Use this Gate to detect increasing errors in your current build versus a given baseline."/]
    </summary>
    [@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.activeTimespan" name="activeTimespan" required="false" description="The time window to search for new and increasing errors. Set to zero to use the entire deployment time window. Supported values are: <strong>d</strong> - day, <strong>h</strong> - hour, <strong>m</strong> - minute. For example: <code class='inline'>12h</code>"/]
    [@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.baselineTimespan" name="baselineTimespan" required="false" description="The time window against which events in the active window are compared to test for regressions. Supported values are: <strong>d</strong> - day, <strong>h</strong> - hour, <strong>m</strong> - minute. For example: <code class='inline'>7d</code>"/]
    [@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.minVolumeThreshold" name="minVolumeThreshold" required="false" description="The minimal number of times an event of a non-critical type must take place to be considered critical."/]
    [@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.minRateThreshold" name="minRateThreshold" required="false" description="The minimum rate at which event of a non-critical type must take place to be considered critical."/]
    [@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.regressionDelta" name="regressionDelta" required="false" description="The change in percentage between an event's rate in the active time span compared to the baseline to be considered a regression."/]
    [@ww.textfield labelKey="com.overops.plugins.bamboo.task.config.criticalRegressionThreshold" name="criticalRegressionThreshold" required="false" description="The change in percentage between an event's rate in the active time span compared to the baseline to be considered a critical error."/]
    [@ww.checkbox labelKey="com.overops.plugins.bamboo.task.config.applySeasonality" name="applySeasonality" required="false" description="If peaks have been seen in baseline window, then this would be considered normal and not a regression. If an equal or matching peak exists in the baseline time window, or two peaks of greater than 50% of the volume seen in the active window, the event will not be marked as a regression."/]
</details>

<h3>Advanced settings</h3>
[@ww.checkbox labelKey="com.overops.plugins.bamboo.task.config.debug" name="debug" required="false" description="If checked, all queries with results will be displayed in the OverOps reliability report. <em>For debugging purposes only.</em>"/]

<script type="text/javascript">
    (() => {
        const checkboxSelector = '.quality-gate-options .toggle-details';
        const detailsSelector = 'details.quality-gate-options';

        // sync details open state with checkbox state
        document.querySelectorAll(checkboxSelector).forEach((element) => {
            element.addEventListener('click', event => {
                setTimeout(() => {
                    element.closest(detailsSelector).open = element.checked;
                }, 10); // event can fire before checked value has updated
            });
        });

        // sync checkbox state with details open state
        document.querySelectorAll(detailsSelector).forEach((element) => {
            element.addEventListener('toggle', event => {
                setTimeout(() => {
                    element.querySelector(checkboxSelector).checked = element.open;
                }, 10); // event can fire before open value is updated
            });
        });
    })();
</script>