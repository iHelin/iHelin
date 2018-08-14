<style>
    .tableRegisterPreview {
        width: 100%;
        margin-top: 8px;
        border: 0;
        border-collapse: collapse;
        border: 1px solid #bbb;
    }

    .tableRegisterPreview tr td {
        border: solid 1px black;
        height: 40px;
        background-color: #fff;
        vertical-align: middle;
        word-break: break-word;
        padding-left: 3px;
        width: 80px;
        text-align: center;
    }

    .tableRegisterPreview tbody th {
        border: solid 1px black;
        height: 40px;
        background-color: #fff;
        vertical-align: middle;
        word-break: break-word;
        padding-left: 3px;
        width: 80px;
        text-align: center;
    }

    .tableRegisterPreview table tbody tr td {
        border: solid 1px black;
        height: 50px;
        background-color: #fff;
        vertical-align: middle;
        word-break: break-word;
        padding-left: 3px;
        width: 80px;
        text-align: center;
    }

    h5 {
        font-weight: bolder;
    }

    td div {
        text-align: center;
    }

    .tableRegisterPreview tbody tr th {
        background-color: #FFFECE;
    }
</style>
<div style="width:100%;font-weight:bloder;">
    <h1>就餐时间：${mealTime}</h1>
    <p style="margin: 10px auto 0 auto; padding: 0; color: #999;">A2 2楼</p>
    <table class="tableRegisterPreview">
        <tbody>
        <tr>
            <th>类型</th>
            <th>菜</th>
        </tr>
        <#list a22f as a>
         <tr>
             <td>${a['key']}</td>
             <td>
                <#if a['value'][currentIndex]??>${a['value'][currentIndex]}</#if>
                <#if a['value'][currentIndex+7]??> + ${a['value'][currentIndex+7]}</#if>
             </td>
         </tr>
        </#list>
        </tbody>
    </table>
    <p style="margin: 10px auto 0 auto; padding: 0; color: #999;">A2 3楼</p>
    <table class="tableRegisterPreview">
        <tbody>
        <tr>
            <th>类型</th>
            <th>菜</th>
        </tr>
        <#list a23f as a>
         <tr>
             <td>${a['key']}</td>
             <td>
                <#if a['value'][currentIndex]??>${a['value'][currentIndex]}</#if>
                <#if a['value'][currentIndex+5]??> + ${a['value'][currentIndex+5]}</#if>
             </td>
         </tr>
        </#list>
        </tbody>
    </table>
    <p style="margin: 10px auto 0 auto; padding: 0; color: #999;">A5 5楼</p>
    <table class="tableRegisterPreview">
        <tbody>
        <tr>
            <th>类型</th>
            <th>菜</th>
        </tr>
        <#list a55f as a>
        <tr>
            <td>${a['key']}</td>
            <td>
                <#if a['value'][currentIndex]??>${a['value'][currentIndex]}</#if>
                <#if a['value'][currentIndex+7]??> + ${a['value'][currentIndex+7]}</#if>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
    <p style="margin: 20px auto 0 auto; padding: 0; color: #999;text-align: center;">温馨提示：此邮件由系统自动发送，请勿回复！</p>
</div>
