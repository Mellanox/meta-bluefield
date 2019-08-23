# This is needed so incremental builds work.  Yocto calls
# make clean when do_configure runs and this particular
# component's Makefile has a problem with that.  We need
# to verify this is still required when we upgrade poky.
CLEANBROKEN = "1"
