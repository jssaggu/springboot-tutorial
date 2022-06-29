<html>
<head>
    <title>${title}</title>
</head>
<body>
<h1>${title}</h1>

<#if metadata.hints??>
    <section style= "border: 2px solid powderblue;">
        <h1>Hints</h1>
        <#list metadata.hints as hint>
            <section style= "border: 2px solid powderblue;">
                <dl>
                    <dt><b>name: </b>${hint.name}</dt>
                    <#if hint.values?? && hint.values?size != 0>
                        <dt><b>values:</b></dt>
                        <#list hint.values as value>
                            <#if value.value??>
                                <dd><b>value: </b>${value.value}</dd>
                            </#if>
                            <#if value.description??>
                                <dd><b>description: </b>${value.description}</dd>
                            </#if>
                            <br/>
                        </#list>
                    </#if>
                    <#if hint.providers?? && hint.providers?size != 0>
                    <dt><b>providers:</b></dt>
                    <#list hint.providers as provider>
                        <#if provider.name??>
                            <dd><b>name: </b>${provider.name}</dd>
                        </#if>
                        <#if provider.parameters?? && provider.parameters?size != 0>
                            <dd><b>parameters:</b></dd>
                            <#list provider.parameters as key, value>
                                <dd>&nbsp;&nbsp;&nbsp;&nbsp;<b>${key}: </b>${value}
                                </dd>
                            </#list>
                        </#if>
                        <br/>
                    </#list>
                </dl>
                </#if>
            </section>
        </#list>
    </section>
</#if>

<#if metadata.items??>
    <section style= "border: 2px solid powderblue;">
        <h1>Groups</h1>
        <#list metadata.items as item>
            <#if item.isOfItemType(GROUP)>
                <section style= "border: 2px solid powderblue;">
                    <dl>
                        <#if item.sourceType??>
                            <dt><b>sourceType: </b> ${item.sourceType}</dt>
                        </#if>
                        <#if item.name??>
                            <dt><b>name: </b> ${item.name}</dt>
                        </#if>
                        <#if item.description??>
                            <dt><b>description: </b> ${item.description}</dt>
                        </#if>
                        <#if item.type??>
                            <dt><b>type: </b> ${item.type}</dt>
                        </#if>
                        <#if item.sourceMethod??>
                            <dt><b>sourceMethod: </b> ${item.sourceMethod}</dt>
                        </#if>
                        <#if item.defaultValue??>
                            <#if item.defaultValue?is_enumerable>
                                <dt><b>defaultValue: </b></dt>
                                <#list item.defaultValue as df>
                                    <dd>${df}</dd>
                                </#list>
                            <#else>
                                <dt><b>defaultValue: </b> ${item.defaultValue}</dt>
                            </#if>
                        </#if>
                        <#if item.deprecation??>
                            <dt><b>deprecation: </b></dt>
                            <#if item.deprecation.reason??>
                                <dd><b>reason: </b> ${item.deprecation.reason}</dd>
                            </#if>
                            <#if item.deprecation.replacement??>
                                <dd><b>replacement: </b> ${item.deprecation.replacement}</dd>
                            </#if>
                            <#if item.deprecation.level??>
                                <dd><b>level: </b> ${item.deprecation.level}</dd>
                            </#if>
                        </#if>
                    </dl>
                </section>
            </#if>
        </#list>
        <h1>Properties</h1>
        <#list metadata.items as item>
            <#if item.isOfItemType(PROPERTY)>
                <section style= "border: 2px solid powderblue;">
                    <dl>
                        <#if item.sourceType??>
                            <dt><b>sourceType: </b> ${item.sourceType}</dt>
                        </#if>
                        <#if item.name??>
                            <dt><b>name: </b> ${item.name}</dt>
                        </#if>
                        <#if item.description??>
                            <dt><b>description: </b> ${item.description}</dt>
                        </#if>
                        <#if item.type??>
                            <dt><b>type: </b> ${item.type}</dt>
                        </#if>
                        <#if item.sourceMethod??>
                            <dt><b>sourceMethod: </b> ${item.sourceMethod}</dt>
                        </#if>
                        <#if item.defaultValue??>
                            <#if item.defaultValue?is_enumerable>
                                <dt><b>defaultValue: </b></dt>
                                <#list item.defaultValue as df>
                                    <dd>${df}</dd>
                                </#list>
                            <#else>
                                <dt><b>defaultValue: </b> ${item.defaultValue}</dt>
                            </#if>
                        </#if>
                        <#if item.deprecation??>
                            <dt><b>deprecation: </b></dt>
                            <#if item.deprecation.reason??>
                                <dd><b>reason: </b> ${item.deprecation.reason}</dd>
                            </#if>
                            <#if item.deprecation.replacement??>
                                <dd><b>replacement: </b> ${item.deprecation.replacement}</dd>
                            </#if>
                            <#if item.deprecation.level??>
                                <dd><b>level: </b> ${item.deprecation.level}</dd>
                            </#if>
                        </#if>
                    </dl>
                </section>
            </#if>
        </#list>
    </section>
</#if>

</body>
</html>