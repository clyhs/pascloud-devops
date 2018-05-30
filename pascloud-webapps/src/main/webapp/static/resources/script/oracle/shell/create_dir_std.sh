echo "creating  folder........"
echo "creating  folder cloudpas_space_data"
dir_base=/home/oracle/db_tablespace
mkdir -p $dir_base/$1/PAS_DATA/PAS_SPACE_DATA
echo "creating  folder cloudpas_space_idx"
mkdir -p $dir_base/$1/PAS_DATA/PAS_SPACE_IDX
echo "creating  folder cloudpas_space_tmp"
mkdir -p $dir_base/$1/PAS_DATA/PAS_SPACE_TMP
echo "creating  folder cloudpas_backup"
mkdir -p $dir_base/$1/PAS_BACKUP
echo "creating  folder cloudpas_data_src"
mkdir -p $dir_base/$1/PAS_DATA_SRC
echo "creating  folder cloudpas_arch_log"
mkdir -p $dir_base/$1/PAS_ARCH_LOG
echo "it's done!!!"
