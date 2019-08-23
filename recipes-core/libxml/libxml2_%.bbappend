# The libxml2 recipe normally builds for python3 which
# is the default for yocto/poky/bitbake.  Our tests
# currently rely on python2.  If you want python3 then
# just remove this bbappend file.

PYTHON_PN = "python"
DEPENDS += "python"
RDEPENDS_${PN}-ptest += "python-core"
PYTHON_BASEVERSION = "2.7"
PYTHON_DIR = "python2.7"
