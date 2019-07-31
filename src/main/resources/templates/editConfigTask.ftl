<style>
    .item-group > .item-entry {
        margin-left: 25px;
    }

    .commutator {
        margin-right: 10px;
    }

    .hidden {
        display: none;
    }
</style>

[@ww.textfield labelKey="overops.admin.url" name="url" required='false'/]
[@ww.textfield labelKey="overops.admin.token" name="token" required='false'/]

[@ww.textfield labelKey="overops.config.applicationName" name="applicationName" required='false'/]
[@ww.textfield labelKey="overops.config.deploymentName" name="deploymentName" required='false'/]
[@ww.textfield labelKey="overops.config.serviceId" name="serviceId" required='false'/]
[@ww.textfield labelKey="overops.config.regexFilter" name="regexFilter" required='false'/]
[@ww.checkbox labelKey="overops.config.markUnstable" name="markUnstable" required='false'/]
[@ww.textfield labelKey="overops.config.printTopIssues" name="printTopIssues" required='false'/]

<div class="item-group">
    <div class="parent-item">
            ${newEventsGate}
         [@ww.checkbox labelKey="overops.config.newEventsGate" name="newEventsGate" required='false' class='commutator'/]
    </div>
    <div class="item-entry  [#if newEventsGate??] [#else] hidden [/#if]">
                [@ww.checkbox labelKey="overops.config.newEvents" name="newEvents" required='false' class='newEventsGate'/]
    </div>
</div>

<div class="item-group">
    <div class="parent-item">
        [@ww.checkbox labelKey="overops.config.checkResurfacedErrors" name="checkResurfacedErrors" class="commutator" required='false'/]
    </div>
    <div class="item-entry [#if checkResurfacedErrors??] [#else] hidden [/#if]">
                [@ww.checkbox labelKey="overops.config.resurfacedErrors" name="resurfacedErrors" class="checkResurfacedErrors" required='false'/]
    </div>
</div>

<div class="item-group">
    <div class="parent-item">
        [@ww.checkbox labelKey="overops.config.checkVolumeErrors" name="checkVolumeErrors" class="commutator" required='false'/]
    </div>
    <div class="item-entry [#if checkResurfacedErrors??] [#else] hidden [/#if]">
               [@ww.textfield labelKey="overops.config.maxErrorVolume" name="maxErrorVolume" class="checkVolumeErrors" required='false'/]
    </div>
</div>

<div class="item-group">
    <div class="parent-item">
        [@ww.checkbox labelKey="overops.config.checkUniqueErrors" name="checkUniqueErrors" class="commutator" required='false'/]
    </div>
    <div class="item-entry [#if checkUniqueErrors??] [#else] hidden [/#if]">
               [@ww.textfield labelKey="overops.config.maxUniqueErrors" name="maxUniqueErrors" class="checkUniqueErrors" required='false'/]
    </div>
</div>

<div class="item-group">
    <div class="parent-item">
        [@ww.checkbox labelKey="overops.config.checkCriticalErrors" name="checkCriticalErrors" class="commutator" required='false'/]
    </div>
    <div class="item-entry [#if checkCriticalErrors??] [#else] hidden [/#if]">
             [@ww.textfield labelKey="overops.config.criticalExceptionTypes" name="criticalExceptionTypes" class="checkCriticalErrors" required='false'/]
    </div>
</div>

<div class="item-group">
    <div class="parent-item">
        [@ww.checkbox labelKey="overops.config.checkRegressionErrors" name="checkRegressionErrors" class="commutator" required='false'/]
    </div>
    <div class="item-entry [#if checkRegressionErrors??] [#else] hidden [/#if]">
             [@ww.textfield labelKey="overops.config.activeTimespan" name="activeTimespan" class="checkRegressionErrors" required='false'/]
                [@ww.textfield labelKey="overops.config.baselineTimespan" name="baselineTimespan" class="checkRegressionErrors" required='false'/]
                [@ww.textfield labelKey="overops.config.minVolumeThreshold" name="minVolumeThreshold" class="checkRegressionErrors" required='false'/]
                [@ww.textfield labelKey="overops.config.minErrorRateThreshold" name="minErrorRateThreshold" class="checkRegressionErrors" required='false'/]
                [@ww.textfield labelKey="overops.config.regressionDelta" name="regressionDelta" class="checkRegressionErrors" required='false'/]
                [@ww.textfield labelKey="overops.config.criticalRegressionDelta" name="criticalRegressionDelta" class="checkRegressionErrors" required='false'/]
[@ww.checkbox labelKey="overops.config.applySeasonality" name="applySeasonality" class="checkRegressionErrors" required='false'/]
    </div>
</div>

[@ww.checkbox labelKey="overops.config.debug" name="debug" required='false'/]

<script type="text/javascript">
    jQuery(function () {
        jQuery('.commutator').change(function () {
            var id = jQuery(this).attr('id');
            if (jQuery(this).is(':checked')) {
                jQuery("." + id).each(function () {
                    if (jQuery(this).hasClass("checkbox")) {
                        jQuery(this).prop('checked', true);
                    }
                });
                jQuery("." + id).closest(".item-entry").removeClass("hidden");
            } else {
                jQuery("." + id).each(function () {
                    if (jQuery(this).hasClass("text")) {
                        jQuery(this).val("")
                    } else if (jQuery(this).hasClass("checkbox")) {
                        jQuery(this).prop('checked', false);
                    }
                });
                jQuery("." + id).closest(".item-entry").addClass("hidden");
            }
        });
    })
</script>