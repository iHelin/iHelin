<style>
    .tableRegisterPreview {
        width: 100%;
        margin-top: 8px;
        border: 0;
        border-collapse: collapse;
        border: 1px solid #bbb;
    }

    .divoptitlePreview {
        font-size: 13px;
        color: #000;
        float: left;
        width: 100px;
        padding-left: 3px;
        font-weight: bold;
        background-color: #eee;
    }

    .divopctrlPreview {
        float: left;
        padding-left: 5px;
        background: #fff;
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


    .tableRegisterPreview .title_class {
        padding-left: 0px;
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
    <p style="margin: 10px auto 0 auto; padding: 0; color: #999;">您的提醒已送达，请知晓:</p>
    <table class="tableRegisterPreview">
        <tbody>
        <tr>
            <th>分数</th>
            <th>理由</th>
            <th>加分时间</th>
            <th>总分</th>
        </tr>
        <tr>
            <td><#if (score.value)??>#{score.value}</#if></td>
            <td>${score.reason!'未填写'}</td>
            <td><#if (score.createTime)??>${score.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
            <td><#if (total)??>#{total}</#if></td>
        </tr>
        </tbody>
    </table>
    <p style="margin: 20px auto 0 auto; padding: 0; color: #999;text-align: center;">温馨提示：此邮件由系统自动发送，请勿回复！</p>
</div>
