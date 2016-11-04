define(function(require, exports, module) {
    mocha.setup('bdd');

    // 相对路径开头，不会解析seajs的path
    
    // scaffold
    require('./scaffold/utilTest');
    require('./scaffold/pathTest');

    // entity
    require('./entity/contract/leasingBillModelTest');

    mocha.run();
});