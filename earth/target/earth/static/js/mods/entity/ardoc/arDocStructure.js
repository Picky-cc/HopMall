define(function(require, exports, module) {

    var ARDocColumn = Backbone.Model.extend({
        defaults: function() {
            return {
                uuid: -1,
                displayName: '',
                interalName: '',
                matchPatterns: '',
                dataType: 'string'
            };
        }
    });

    var ARDocBlockStructure = Backbone.Model.extend({
        initialize: function(attr, options) {
            options || (options = {});
            this.key = options.key;
            
            // this.columns = new Backbone.Collection(this.get('columns'), {
            //     model: ARDocColumn
            // });
            // this.columns.on('add remove', _.bind(resetColumns, this));

            // this.on('change:columns', function(model) {
            //     this.columns.reset(model.get('columns'));
            // });
            // function resetColumns() {
            //     var temp = this.columns.toJSON();
            //     this.set({
            //         columns: temp
            //     }, {
            //         silent: true
            //     });
            // }
        },
        addColumn: function(attr) {
            // this.trigger('add:columns');
            // this.columns.add(attr);

            var source = this.get('columns');
            source[_.uniqueId()] = attr;
            this.set('columns', source);
        },
        removeColumn: function(filterParam) {
            // this.trigger('remove:columns');
            // return this.columns.remove(this.getColumn(filterParam, false));

            var source = this.get('columns');
            var dels = this.getColumn(filterParam);
            var current = {};
            _.each(source, function(val, key) {
                if (dels.indexOf(val) === -1) {
                    current[key] = val;
                }
            });
            this.set('columns', current);
            return dels;
        },
        getColumn: function(filterParam, isPack) { // filterParam为{}或undefined，返回所有数据
            // if(_.isBoolean(filterParam)) {
            //    isPack=filterParam;
            //    filterParam={};
            // }
            // var results = this.columns.where(filterParam);
            // return isPack === true ? results : _.map(results, function (model) {
            //    return model.toJSON();
            // });

            var source = this.get('columns');
            var arr = _.map(source, function(val) {
                return val;
            });
            return _.where(arr, filterParam || {});
        },
        validate: function(attr) {
            if (!attr.uuid || !attr.name || !_.isArray(attr.columns))
                return 'error';
        }
    });


    function ARDocTotalStructure(source, options) {
        _.isString(source) ? (this.structure = JSON.parse(source)) : (this.structure = source);
        if (!this._validate(this.structure)) throw '模版结构定义不正确';
        this.options = options || {};
        this.blocks = [];
        this.init();
    }
    ARDocTotalStructure.prototype = {
        constructor: ARDocTotalStructure,
        init: function() {
            this._extractBlocks();
            this.createDetailInfoViewTemplate();
        },
        _validate: function(structureObj) {
            if (!structureObj.uuid || !structureObj.name || !_.isObject(structureObj.documents))
                return false;
            return true;
        },
        _extractBlocks: function() {
            var blocks = this.structure.documents;
            for (var prop in blocks) {
                this.addBlockStructure(prop, blocks[prop]);
            }
            this.sortBlocks();
            return this;
        },
        sortBlocks: function () {
            var _block = this.blocks;
            // 权重表
            var weight = {
                a_rentContract:10,
                b_hostingContract: 8,
                c_assetPackage: 6,
                audit: 4
            };
            _block.sort(function (prev, next) {
                var weightPrev = weight[prev.key] || Math.random();
                var weightNext = weight[next.key] || Math.random();
                return weightNext > weightPrev;
            });
        },
        createDetailInfoViewTemplate: function(newCreateDetailViewTmpl) {
            newCreateDetailViewTmpl && (this.options.createDetailViewTmpl = newCreateDetailViewTmpl);
            var temp = this.options.createDetailViewTmpl;
            var res = '';
            if (!temp) return;
            else if (_.isString(temp))
                res = temp;
            else if (_.isFunction(temp))
                res = temp();
            else if (_.isElement(temp.get(0)))
                res = $(temp).html();

            this.detailViewTmpl = _.template(res)(this.structure);
            return this.detailViewTmpl;
        },
        extractColumnsForTablePlugins: function() {
            var res = this._extractColumnsInNormalBlocks();
            res.normal = _.map(res.normal, function(val, key) {
                return {
                    field: val.interalName,
                    title: val.displayName
                };
            });
            res.audit = _.map(res.audit, function (val, key) {
            	if(val.interalName == 'auditStatus') {
            		return {
                        field: val.interalName,
                        title: '审核状态'
                    };
            	}else {
                    return {
                        field: val.interalName,
                        title: val.displayName
                    };
            	}
            });
            return res;
        },
        _extractColumnsInNormalBlocks: function() {
            var res = {
                normal: [],
                audit: []
            };
            var blocks = this.blocks;
            for (var i = 0, len = blocks.length; i < len; i++) {
                if(blocks[i].key === 'audit') {
                    res.audit = res.audit.concat(blocks[i].getColumn());
                }else {
                    res.normal = res.normal.concat(blocks[i].getColumn());
                }
            }
            return res;
        },
        removeBlockByKey: function(key) {
            var blocks = this.blocks,
                removedBlocks = [];
            for (var i = 0, len = blocks.length; i < len; i++) {
                if (blocks[i].key !== key) continue;
                removedBlocks = removedBlocks.concat(blocks.splice(i, 1));
            }
            return removedBlocks;
        },
        addBlockStructure: function(key, definitionObj) {
            var newBlock = new ARDocBlockStructure(definitionObj, {
                key: key
            });
            this.blocks.push(newBlock);
            return newBlock;
        },
        getBlockStructure: function(key) {
            var blocks = this.blocks;
            for (var i = 0, len = blocks.length; i < len; i++) {
                if (blocks[i].key === key) return blocks[i];
            }
            return null;
        }
    };

    exports.ARDocBlockStructure = ARDocBlockStructure;
    exports.ARDocTotalStructure = ARDocTotalStructure;
});